package com.example.ppompai.server.group.domain;

import jakarta.validation.constraints.NotBlank;

public final class GroupInviteRequest {
    @NotBlank
    public Long groupId;

    @NotBlank
    public Long inviteeId;
}
