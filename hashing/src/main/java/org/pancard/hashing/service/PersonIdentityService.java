package org.pancard.hashing.service;


import org.pancard.hashing.dto.PANMaskedResponse;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public interface PersonIdentityService {

    PANMaskedResponse savePAN(UUID userId, String pan, UUID callerUserId) throws NoSuchAlgorithmException;

    PANMaskedResponse getMaskedPAN(UUID userId);

    boolean verifyPAN(UUID userId, String pan) throws NoSuchAlgorithmException;

    void softDelete(UUID personId, String reason, UUID callerUserId);
}
