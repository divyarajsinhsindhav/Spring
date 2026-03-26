package org.pancard.hashing.repository;

import org.pancard.hashing.entity.PersonIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonIdentityRepository extends JpaRepository<PersonIdentity, UUID> {

    Optional<PersonIdentity> findByUserIdAndDeletedAtIsNull(UUID userId);

    Optional<PersonIdentity> findByPersonIdAndDeletedAtIsNull(UUID personId);

    boolean existsByPanHashAndDeletedAtIsNull(String panHash);
}
