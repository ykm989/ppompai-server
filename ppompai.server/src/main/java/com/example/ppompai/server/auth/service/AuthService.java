package com.example.ppompai.server.auth.service;

import com.example.ppompai.server.auth.domain.*;
import com.example.ppompai.server.auth.repository.UserRefreshTokenRepository;
import com.example.ppompai.server.auth.repository.UserRepository;
import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public TokenDTO signup(SignupRequest signupRequest) {
        try {
            if (!userRepository.findByEmail(signupRequest.getEmail()).isEmpty()) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }

            String encodePw = passwordEncoder.encode(signupRequest.getPassword());

            User user = new User();
            user.email = signupRequest.getEmail();
            user.password = encodePw;
            userRepository.save(user);

            String accessToken = jwtTokenProvider.createAccessToken(user);
            String refreshToken = jwtTokenProvider.createRefreshToken(user);

            UserRefreshToken urt = new UserRefreshToken(
                    user.id,
                    refreshToken,
                    null
            );
            userRefreshTokenRepository.save(urt);

            return new TokenDTO(accessToken, refreshToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
