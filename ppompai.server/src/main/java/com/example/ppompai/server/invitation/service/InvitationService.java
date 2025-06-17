package com.example.ppompai.server.invitation.service;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.Group;
import com.example.ppompai.server.common.domain.Invitation;
import com.example.ppompai.server.common.domain.User;
import com.example.ppompai.server.common.repository.GroupRepository;
import com.example.ppompai.server.common.repository.InvitationRepository;
import com.example.ppompai.server.invitation.domain.GroupInvitationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final GroupRepository groupRepository;
    private final InvitationRepository invitationRepository;

    public ResponseEntity<ApiResponse<?>> getInvitationList(User user) {
        try {
            List<Invitation> invitationList = invitationRepository.findByinvitee(user);
            return ResponseEntity.ok(ApiResponse.success(invitationList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }

    // 초대 보내기
    public ResponseEntity<ApiResponse<?>> sendInvite(GroupInvitationRequest request, User user) {
        try {
            Group group = groupRepository.findByOwnerAndGroupId(user, request.groupId);

            if (group != null) {
                Invitation invitation = Invitation.builder()
                        .invitee(user)
                        .group(group)
                        .status("Invited")
                        .build();

                invitationRepository.save(invitation);
            }

            return ResponseEntity
                    .ok(ApiResponse.success());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // 초대 거절하기
    public ResponseEntity<ApiResponse<?>> rejectInvite(Long invitationid, User user) {
        try {
            Invitation invitation = invitationRepository.findByInvitationId(invitationid);

            if (invitation.invitee.equals(user)) {
                invitationRepository.delete(invitation);
            }

            boolean exists = invitationRepository.existsById(invitationid);
            if(exists) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(ApiResponse.fail("Invitation Rejected Fail"));
            }

            return ResponseEntity
                    .ok(ApiResponse.success());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
