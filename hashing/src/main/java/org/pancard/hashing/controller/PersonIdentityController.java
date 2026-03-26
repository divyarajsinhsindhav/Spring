package org.pancard.hashing.controller;

import jakarta.validation.Valid;
import org.pancard.hashing.dto.ApiResponse;
import org.pancard.hashing.dto.PANMaskedResponse;
import org.pancard.hashing.dto.request.PANSubmitRequest;
import org.pancard.hashing.service.PersonIdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/identity")
public class PersonIdentityController {

    private static final Logger log = LoggerFactory.getLogger(PersonIdentityController.class);

    private final PersonIdentityService service;

    public PersonIdentityController(PersonIdentityService service) {
        this.service = service;
    }

    @PostMapping("/pan")
    public ResponseEntity<ApiResponse<PANMaskedResponse>> submitPAN(
            @Valid @RequestBody PANSubmitRequest request,
            @AuthenticationPrincipal String userId) throws NoSuchAlgorithmException {

        UUID uid      = UUID.fromString(userId);
        PANMaskedResponse response = service.savePAN(uid, request.getPan(), uid);

        log.info("PAN submit for userId={}", uid);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("PAN saved successfully", response));
    }

    @GetMapping("/pan")
    public ResponseEntity<ApiResponse<PANMaskedResponse>> getMaskedPAN(
            @AuthenticationPrincipal String userId) {

        UUID uid = UUID.fromString(userId);
        return ResponseEntity.ok(ApiResponse.ok("PAN fetched", service.getMaskedPAN(uid)));
    }

    @PostMapping("/pan/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyPAN(
            @Valid @RequestBody PANSubmitRequest request,
            @AuthenticationPrincipal String userId) throws NoSuchAlgorithmException {

        UUID uid      = UUID.fromString(userId);
        boolean matches = service.verifyPAN(uid, request.getPan());
        return ResponseEntity.ok(ApiResponse.ok("PAN verified", matches));
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<ApiResponse<Void>> deletePAN(
            @PathVariable UUID personId,
            @RequestParam String reason,
            @AuthenticationPrincipal String userId) {

        UUID callerId = UUID.fromString(userId);
        service.softDelete(personId, reason, callerId);
        return ResponseEntity.ok(ApiResponse.ok("PAN record deleted", null));
    }
}