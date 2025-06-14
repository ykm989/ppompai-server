package com.example.ppompai.server.group.domain;

import com.example.ppompai.server.common.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupCreateRequest {
    @NotBlank
    private String groupName;
}
