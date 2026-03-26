package org.pancard.hashing.dto;

import java.util.UUID;

public class PANMaskedResponse {

    private UUID   personId;
    private UUID   userId;
    private String maskedPan;
    private String createdAt;

    public PANMaskedResponse(UUID personId, UUID userId,
                             String maskedPan, String createdAt) {
        this.personId  = personId;
        this.userId    = userId;
        this.maskedPan = maskedPan;
        this.createdAt = createdAt;
    }

    public UUID   getPersonId()  { return personId; }
    public UUID   getUserId()    { return userId; }
    public String getMaskedPan() { return maskedPan; }
    public String getCreatedAt() { return createdAt; }
}
