package org.pancard.hashing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PANSubmitRequest {

    @NotBlank(message = "PAN is required")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$",
             message = "PAN must match format: 5 uppercase letters, 4 digits, 1 uppercase letter")
    private String pan;

    public PANSubmitRequest() {}

    public PANSubmitRequest(String pan) {
        this.pan = pan;
    }

    public String getPan() { return pan; }
    public void   setPan(String pan) { this.pan = pan; }
}
