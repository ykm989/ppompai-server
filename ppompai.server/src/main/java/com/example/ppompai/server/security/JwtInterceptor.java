package com.example.ppompai.server.security;

import com.example.ppompai.server.auth.repository.UserRepository;
import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization").substring(7);

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");

            ApiResponse<Void> body = ApiResponse.fail("Invalid Token");
            String json = objectMapper.writeValueAsString(body);

            response.getWriter().write(json);
            response.getWriter().flush();

            return false;
        }

        String userEmail = jwtTokenProvider.getEmail(token);
        Optional<User> optionalUser = userRepository
                .findByEmail(userEmail);

        if (optionalUser.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");

            ApiResponse<Void> body = ApiResponse.fail("Invalid Token");
            String json = objectMapper.writeValueAsString(body);

            response.getWriter().write(json);
            response.getWriter().flush();

            return false;
        }

        User user = optionalUser.get();

        request.setAttribute("user", user);

        return true;
    }
}