package org.pancard.hashing.controller;

import jakarta.validation.Valid;
import org.pancard.hashing.dto.ApiResponse;
import org.pancard.hashing.dto.request.LoginRequest;
import org.pancard.hashing.dto.response.LoginResponse;
import org.pancard.hashing.service.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private static final UUID MOCK_USER_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        String token  = jwtUtil.generateToken(MOCK_USER_ID.toString());
        LoginResponse body = new LoginResponse(token, MOCK_USER_ID.toString());

        log.info("Login issued for username={}, mockUserId={}", request.getUsername(), MOCK_USER_ID);

        return ResponseEntity.ok(ApiResponse.ok("Login successful", body));
    }
}
