package com.example.ppompai.server.group.controller;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.Group;
import com.example.ppompai.server.group.domain.GroupCreateRequest;
import com.example.ppompai.server.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createGroup(@RequestBody GroupCreateRequest request) {
        return groupService.createGroup(request);
    }
}
