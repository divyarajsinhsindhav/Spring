package org.pancard.hashing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "person_identity")
@Data
@RequiredArgsConstructor
public class PersonIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "person_id")
    private UUID personId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "pan_hash", nullable = false, length = 64)
    private String panHash;

    @Column(name = "pan_first3", nullable = false, length = 3)
    private String panFirst3;

    @Column(name = "pan_last2", nullable = false, length = 2)
    private String panLast2;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "deletion_reason", length = 100)
    private String deletionReason;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt = OffsetDateTime.now();
}
