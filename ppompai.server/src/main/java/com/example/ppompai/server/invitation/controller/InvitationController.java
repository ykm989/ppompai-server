package com.example.ppompai.server.invitation.controller;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.User;
import com.example.ppompai.server.invitation.domain.GroupInvitationRequest;
import com.example.ppompai.server.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    // 받은 초대 목록 가져오기
    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getInvitation(
            @RequestAttribute User user
            ) {
        return invitationService.getInvitationList(user);
    }

    // 그룹 유저 초대
    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> sendInvite(
            @RequestAttribute User user,
            @RequestBody GroupInvitationRequest request
    ) {
        return invitationService.sendInvite(request, user);
    }

    // 초대 거절하기
    @DeleteMapping("/reject")
    public ResponseEntity<ApiResponse<?>> rejectInvite(
            @RequestAttribute User user,
            @RequestHeader Long invitationId
    ) {
        return invitationService.rejectInvite(invitationId, user);
    }
}
