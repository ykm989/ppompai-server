package com.example.ppompai.server.group.controller;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.group.domain.GroupCreateRequest;
import com.example.ppompai.server.group.domain.GroupDeleteRequest;
import com.example.ppompai.server.group.domain.GroupInviteRequest;
import com.example.ppompai.server.group.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createGroup(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String accessToken,
            @RequestBody GroupCreateRequest request
    ) {
        String token = accessToken.substring(7);
        return groupService.createGroup(request, token);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getUsersGroups(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.substring(7);
        return groupService.getUsersGroup(token);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping()
    public ResponseEntity<ApiResponse<?>> deleteGroup(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String accessToken,
            @RequestBody GroupDeleteRequest request
    ) {
        String token = accessToken.substring(7);
        return groupService.deleteGroup(token, request.groupId);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/invite")
    public ResponseEntity<ApiResponse<?>> sendInvite(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String accessToken,
            @RequestBody GroupInviteRequest request
    ) {
        String token = accessToken.substring(7);
        return groupService.sendInvite(request, token);
    }
}
