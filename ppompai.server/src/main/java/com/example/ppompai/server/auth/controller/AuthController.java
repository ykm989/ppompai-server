package com.example.ppompai.server.auth.controller;

import com.example.ppompai.server.auth.domain.LoginRequest;
import com.example.ppompai.server.auth.domain.RefreshRequest;
import com.example.ppompai.server.auth.domain.SignupRequest;
import com.example.ppompai.server.auth.domain.TokenDTO;
import com.example.ppompai.server.auth.service.AuthService;
import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signUp(@RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refresh(@RequestBody RefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/signout")
    public ResponseEntity<ApiResponse<?>> signOut(
            @RequestAttribute User user
    ) {
        return authService.signout(user);
    }
}
