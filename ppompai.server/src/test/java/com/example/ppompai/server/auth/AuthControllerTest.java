package com.example.ppompai.server.auth;

import com.example.ppompai.server.auth.controller.AuthController;
import com.example.ppompai.server.auth.domain.SignupRequest;
import com.example.ppompai.server.auth.service.AuthService;
import com.example.ppompai.server.common.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 API 테스트")
    void Signup_Test() throws Exception {
        // given
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        // Mock Service가 반환할 응답 정의
        when(authService.signup(any())).thenReturn(
                ResponseEntity.ok(ApiResponse.success("회원가입 성공"))
        );

        // when & then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"));
//                .andExpect(jsonPath("$.data").value("회원가입 성공"));
    }
}
