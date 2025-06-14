//package com.example.ppompai.server.group.service;
//
//import com.example.ppompai.server.auth.repository.UserRepository;
//import com.example.ppompai.server.common.ApiResponse;
//import com.example.ppompai.server.common.domain.Group;
//import com.example.ppompai.server.common.domain.User;
//import com.example.ppompai.server.group.domain.GroupCreateRequest;
//import com.example.ppompai.server.common.repository.GroupRepository;
//import com.example.ppompai.server.security.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class GroupService {
//    private final GroupRepository groupRepository;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final UserRepository userRepository;
//
//    // 그룹 생성
//    public ResponseEntity<ApiResponse<?>> createGroup(GroupCreateRequest request, String accessToken) {
//        try {
//
//            if(!jwtTokenProvider.validateToken(accessToken)) {
//                return ResponseEntity
//                        .status(HttpStatus.UNAUTHORIZED)
//                        .body(ApiResponse.fail("Invalid Token"));
//            }
//
//            String userEmail = jwtTokenProvider.getEmail(accessToken);
//            Optional<User> optionalUser = userRepository
//                    .findByEmail(userEmail);
//
//            if (optionalUser.isEmpty()) {
//                return ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .body(ApiResponse.fail("User Not Found"));
//            }
//
//            User user = optionalUser.get();
//
//            Group newGroup = Group.builder()
//                    .groupName(request.getGroupName())
//                    .owner(user)
//                    .build();
//
//            if (newGroup == null) {
//                return ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .body(ApiResponse.fail("BAD REQUEST"));
//            }
//
//            groupRepository.save(newGroup);
//
//            return ResponseEntity
//                    .ok(ApiResponse.success(newGroup));
//        }
//        catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.error(e.getMessage()));
//        }
//    }
//
//    // 유저 그룹 목록 가져오기
//    public ResponseEntity<ApiResponse<?>> getUsersGroup(String accessToken) {
//        try {
//            if(!jwtTokenProvider.validateToken(accessToken)) {
//                return ResponseEntity
//                        .status(HttpStatus.UNAUTHORIZED)
//                        .body(ApiResponse.fail("Invalid Token"));
//            }
//
//            String userEmail = jwtTokenProvider.getEmail(accessToken);
//            Optional<User> optionalUser = userRepository
//                    .findByEmail(userEmail);
//
//            if (optionalUser.isEmpty()) {
//                return ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .body(ApiResponse.fail("User Not Found"));
//            }
//
//            User user = optionalUser.get();
//            Set<Group> userGroups = new HashSet<>();
//            System.out.println(userGroups);
//            userGroups.addAll(groupRepository.findByOwner(user));
//            System.out.println(userGroups);
//            userGroups.addAll(groupRepository.findBymembers(user));
//
//            return ResponseEntity
//                    .ok(ApiResponse.success(userGroups));
//        }
//        catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.error(e.getMessage()));
//        }
//    }
//
//    // 그룹 삭제
//    public ResponseEntity<ApiResponse<?>> deleteGroup(String accessToken, String id) {
//        try {
//            long groupId = Long.valueOf(id);
//            if(!jwtTokenProvider.validateToken(accessToken)) {
//                return ResponseEntity
//                        .status(HttpStatus.UNAUTHORIZED)
//                        .body(ApiResponse.fail("Invalid Token"));
//            }
//
//            String userEmail = jwtTokenProvider.getEmail(accessToken);
//            Optional<User> optionalUser = userRepository
//                    .findByEmail(userEmail);
//
//            if (optionalUser.isEmpty()) {
//                return ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .body(ApiResponse.fail("User Not Found"));
//            }
//
//            User user = optionalUser.get();
//            Group group = groupRepository.findByOwnerAndId(user, groupId);
//            groupRepository.delete(group);
//
//            boolean exists = groupRepository.existsById(groupId);
//            if (exists) {
//                return ResponseEntity
//                        .status(HttpStatus.CONFLICT)
//                        .body(ApiResponse.fail("Group Delete Fail"));
//            }
//
//            return ResponseEntity
//                    .ok(ApiResponse.success(group));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.error(e.getMessage()));
//        }
//    }
//}