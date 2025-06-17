package com.example.ppompai.server.invitation.controller;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.User;
import com.example.ppompai.server.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getInvitation(
            @RequestAttribute User user
            ) {
        return invitationService.getInvitationList(user);
    }
}
