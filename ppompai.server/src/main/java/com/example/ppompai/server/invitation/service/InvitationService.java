package com.example.ppompai.server.invitation.service;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.Invitation;
import com.example.ppompai.server.common.domain.User;
import com.example.ppompai.server.common.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;

    public ResponseEntity<ApiResponse<?>> getInvitationList(User user) {
        try {
            List<Invitation> invitationList = invitationRepository.findByinvitee(user);
            return ResponseEntity.ok(ApiResponse.success(invitationList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }
}
