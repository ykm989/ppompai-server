package com.example.ppompai.server.group.service;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.Group;
import com.example.ppompai.server.group.domain.GroupCreateRequest;
import com.example.ppompai.server.common.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private GroupRepository groupRepository;

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
}
