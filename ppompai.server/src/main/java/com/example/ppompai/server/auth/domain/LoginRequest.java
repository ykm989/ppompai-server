package com.example.ppompai.server.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // 기본 생성자
    public LoginRequest() {}
}
