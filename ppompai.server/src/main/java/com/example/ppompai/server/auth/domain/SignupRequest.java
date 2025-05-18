package com.example.ppompai.server.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
