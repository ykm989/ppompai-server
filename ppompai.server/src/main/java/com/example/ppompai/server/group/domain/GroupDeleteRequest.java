package com.example.ppompai.server.group.domain;

import jakarta.validation.constraints.NotBlank;

public final class GroupDeleteRequest {
    @NotBlank
    public String groupId;
}
