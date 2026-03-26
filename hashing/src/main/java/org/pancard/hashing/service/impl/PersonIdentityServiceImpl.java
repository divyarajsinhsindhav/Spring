package org.pancard.hashing.service.impl;

import org.pancard.hashing.dto.PANMaskedResponse;
import org.pancard.hashing.entity.PersonIdentity;
import org.pancard.hashing.exception.PANAlreadyExistsException;
import org.pancard.hashing.exception.PersonIdentityNotFoundException;
import org.pancard.hashing.repository.PersonIdentityRepository;
import org.pancard.hashing.service.PersonIdentityService;
import org.pancard.hashing.utils.PANHashingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class PersonIdentityServiceImpl implements PersonIdentityService {

    private static final Logger log = LoggerFactory.getLogger(PersonIdentityServiceImpl.class);

    private final PersonIdentityRepository repository;
    private final PANHashingUtil hashingUtil;

    public PersonIdentityServiceImpl(PersonIdentityRepository repository,
                                     PANHashingUtil hashingUtil) {
        this.repository  = repository;
        this.hashingUtil = hashingUtil;
    }

    @Override
    public PANMaskedResponse savePAN(UUID userId, String pan, UUID callerUserId)
            throws NoSuchAlgorithmException {

        if (!hashingUtil.isValidPAN(pan)) {
            throw new IllegalArgumentException("Invalid PAN format");
        }

        String hash = hashingUtil.hash(pan);

        if (repository.existsByPanHashAndDeletedAtIsNull(hash)) {
            throw new PANAlreadyExistsException("PAN already registered");
        }

        PersonIdentity identity = new PersonIdentity();
        identity.setUserId(userId);
        identity.setPanHash(hash);
        identity.setPanFirst3(hashingUtil.extractFirst3(pan));
        identity.setPanLast2(hashingUtil.extractLast2(pan));
        identity.setCreatedBy(callerUserId);

        PersonIdentity saved = repository.save(identity);

        log.info("PAN saved for userId={}, maskedPan={}",
                userId, hashingUtil.mask(saved.getPanFirst3(), saved.getPanLast2()));

        return toMaskedResponse(saved);
    }

    @Override
    public PANMaskedResponse getMaskedPAN(UUID userId) {
        PersonIdentity identity = repository
                .findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new PersonIdentityNotFoundException(
                        "No PAN found for user: " + userId));

        log.info("Fetched masked PAN for userId={}", userId);
        return toMaskedResponse(identity);
    }

    @Override
    public boolean verifyPAN(UUID userId, String pan) throws NoSuchAlgorithmException {
        PersonIdentity identity = repository
                .findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new PersonIdentityNotFoundException(
                        "No PAN found for user: " + userId));

        boolean matches = hashingUtil.verify(pan, identity.getPanHash());
        log.info("PAN verification for userId={} result={}", userId, matches);
        return matches;
    }

    @Override
    public void softDelete(UUID personId, String reason, UUID callerUserId) {
        PersonIdentity identity = repository
                .findByPersonIdAndDeletedAtIsNull(personId)
                .orElseThrow(() -> new PersonIdentityNotFoundException(
                        "Record not found for personId: " + personId));

        identity.setDeletedAt(OffsetDateTime.now());
        identity.setDeletionReason(reason);
        identity.setUpdatedBy(callerUserId);
        identity.setUpdatedAt(OffsetDateTime.now());
        repository.save(identity);

        log.info("Soft-deleted personId={} by callerId={}", personId, callerUserId);
    }

    private PANMaskedResponse toMaskedResponse(PersonIdentity p) {
        return new PANMaskedResponse(
                p.getPersonId(),
                p.getUserId(),
                hashingUtil.mask(p.getPanFirst3(), p.getPanLast2()),
                p.getCreatedAt().toString()
        );
    }
}
