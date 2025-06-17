package com.example.ppompai.server.group.controller;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.User;
import com.example.ppompai.server.group.domain.GroupCreateRequest;
import com.example.ppompai.server.group.domain.GroupUpdateRequest;
import com.example.ppompai.server.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    // 그룹 생성
    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createGroup(
            @RequestAttribute User user,
            @RequestBody GroupCreateRequest request
    ) {
        return groupService.createGroup(request, user);
    }

    // 그룹 목록 가져오기
    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getUsersGroups(
            @RequestAttribute User user
    ) {
        return groupService.getUsersGroup(user);
    }

    // 그룹 정보 갱신
    @PutMapping()
    public ResponseEntity<ApiResponse<?>> updateGroup(
            @RequestAttribute User user,
            @RequestHeader("groupId") Long groupId,
            @RequestBody GroupUpdateRequest request
    ) {
        return groupService.updateGroup(request, groupId, user);
    }

    // 개별 그룹 정보 조회
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<?>> getGroupInfo(
            @RequestAttribute User user,
            @RequestHeader("groupId") Long groupId
    ) {
        return groupService.getGroupInfo(user, groupId);
    }

    // 그룹 삭제
    @DeleteMapping()
    public ResponseEntity<ApiResponse<?>> deleteGroup(
            @RequestAttribute User user,
            @RequestHeader("groupId") Long groupId
    ) {
        return groupService.deleteGroup(user, groupId);
    }

    // 그룹 탈퇴
    @DeleteMapping("/leave")
    public ResponseEntity<ApiResponse<?>> leaveGroup(
            @RequestAttribute User user,
            @RequestHeader Long groupId
    ) {
        return groupService.leaveGroup(user, groupId);
    }

    // 그룹 멤버 강퇴
    @DeleteMapping("/resign")
    public ResponseEntity<ApiResponse<?>> resignMember(
            @RequestAttribute User user,
            @RequestHeader Long groupId,
            @RequestHeader Long targetId
    ) {
        return groupService.resignMember(user, groupId, targetId);
    }
}