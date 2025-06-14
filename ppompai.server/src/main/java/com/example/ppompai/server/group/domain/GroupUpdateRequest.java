package com.example.ppompai.server.group.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupUpdateRequest {
    @NotBlank
    private String groupName;
}
