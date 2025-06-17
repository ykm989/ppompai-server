package com.example.ppompai.server.invitation.domain;

import jakarta.validation.constraints.NotBlank;

public class GroupInvitationRequest {
        @NotBlank
        public Long groupId;

        @NotBlank
        public Long inviteeId;
}
