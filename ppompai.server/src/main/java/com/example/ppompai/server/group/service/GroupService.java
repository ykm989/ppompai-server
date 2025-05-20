package com.example.ppompai.server.group.service;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.Group;
import com.example.ppompai.server.group.domain.GroupCreateRequest;
import com.example.ppompai.server.common.repository.GroupRepository;
import com.example.ppompai.server.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<ApiResponse<?>> createGroup(GroupCreateRequest request) {
        try {
            Group newGroup = Group.builder()
                    .groupName(request.getGroupName())
                    .owner(request.getOwner())
                    .build();

            if (newGroup == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("BAD REQUEST"));
            }

            groupRepository.save(newGroup);

            return ResponseEntity
                    .ok(ApiResponse.success(newGroup));
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<?>> getUsersGroup(String accessToken) {
        try {
            if(!jwtTokenProvider.validateToken(accessToken)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("invalid refresh token"));
            }

            String userEmail = jwtTokenProvider.getEmail(accessToken);

            Set<Group> userGroups = new HashSet<>();
            userGroups.addAll(groupRepository.findByOwnerEmail(userEmail));
            userGroups.addAll(groupRepository.findByParticipantsEmail(userEmail));

            return ResponseEntity
                    .ok(ApiResponse.success(userGroups));
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
