package com.example.ppompai.server.auth.controller;

import com.example.ppompai.server.auth.domain.SignupRequest;
import com.example.ppompai.server.auth.domain.TokenDTO;
import com.example.ppompai.server.auth.service.AuthService;
import com.example.ppompai.server.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<TokenDTO>> signUp(@RequestBody SignupRequest signupRequest) {
        TokenDTO token = authService.signup(signupRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(token));
    }
}
