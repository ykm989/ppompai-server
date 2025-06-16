package com.example.ppompai.server.security;

import com.example.ppompai.server.auth.repository.UserRepository;
import com.example.ppompai.server.common.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization").substring(7);

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            response.sendError(401, "Invalid token");
            return false;
        }

        String userEmail = jwtTokenProvider.getEmail(token);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();

        request.setAttribute("user", user);

        return true;
    }
}