package com.example.ppompai.server.auth.domain;

import jakarta.validation.constraints.NotBlank;

public class SignupRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // 기본 생성자
    public SignupRequest() {}

    // getter / setter
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
