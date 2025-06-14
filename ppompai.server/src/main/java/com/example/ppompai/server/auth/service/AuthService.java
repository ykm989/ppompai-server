package com.example.ppompai.server.auth.service;

import com.example.ppompai.server.auth.domain.*;
import com.example.ppompai.server.auth.repository.UserRefreshTokenRepository;
import com.example.ppompai.server.auth.repository.UserRepository;
import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.User;
import com.example.ppompai.server.security.JwtTokenProvider;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ApiResponse<?>> signup(SignupRequest signupRequest) {
        try {
            if (!userRepository.findByEmail(signupRequest.getEmail()).isEmpty()) {
                ApiResponse<Void> body = ApiResponse.fail("email already in use");
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(body);
            }

            String encodePw = passwordEncoder.encode(signupRequest.getPassword());

            User user = new User();
            user.email = signupRequest.getEmail();
            user.password = encodePw;
            userRepository.save(user);

            String accessToken = jwtTokenProvider.createAccessToken(user);
            String refreshToken = jwtTokenProvider.createRefreshToken(user);

            UserRefreshToken userRefreshToken = new UserRefreshToken().builder()
                    .user(user)
                    .refreshToken(refreshToken)
                    .build();

            userRefreshTokenRepository.save(userRefreshToken);
            TokenDTO tokenDTO = new TokenDTO(accessToken, refreshToken);

            return ResponseEntity
                    .ok(ApiResponse.success(tokenDTO));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ApiResponse<Void> body = ApiResponse.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(body);
        }
    }

    public ResponseEntity<ApiResponse<?>> login(LoginRequest loginRequest) {
        try {
            String encodePw = passwordEncoder.encode(loginRequest.getPassword());
            Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

            if (userOpt.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().password)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("invalid password"));
            }

            String accessToken = jwtTokenProvider.createAccessToken(userOpt.get());
            String refreshToken = jwtTokenProvider.createRefreshToken(userOpt.get());

            UserRefreshToken userRefreshToken = userRefreshTokenRepository.findById(userOpt.get().userId)
                    .orElseThrow(() -> new IllegalStateException("Refresh token entity not found for user: " + userOpt.get().userId));

            userRefreshToken.setRefreshToken(refreshToken);
            userRefreshTokenRepository.save(userRefreshToken);

            TokenDTO tokenDTO = new TokenDTO(accessToken, refreshToken);

            return ResponseEntity
                    .ok(ApiResponse.success(tokenDTO));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            ApiResponse<Void> body = ApiResponse.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(body);
        }
    }

    public ResponseEntity<ApiResponse<?>> refresh(RefreshRequest refreshRequest) {
        try {
            String oldRefreshToken = refreshRequest.getRefreshToken();

            if (!jwtTokenProvider.validateToken(oldRefreshToken)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("invalid refresh token"));
            }

            String userEmail = jwtTokenProvider.getUserEmailByRefresh(oldRefreshToken);
            User user = userRepository.findByEmail(userEmail).orElseThrow(
                    () -> new InvalidRequestStateException("user does not exist")
            );

            Optional<UserRefreshToken> refreshTokenOptional = userRefreshTokenRepository.findByUser(user);

            if (refreshTokenOptional.isEmpty() || refreshTokenOptional.get().equals(oldRefreshToken)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("invalid refresh token"));
            }

            String newAccessToken = jwtTokenProvider.createAccessToken(user);
            String newRefreshToken = jwtTokenProvider.createRefreshToken(user);

            TokenDTO tokenDTO = new TokenDTO(newAccessToken, newRefreshToken);

            return ResponseEntity
                    .ok(ApiResponse.success(tokenDTO));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ApiResponse<Void> body = ApiResponse.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(body);
        }
    }

    public ResponseEntity<ApiResponse<?>> signout(String accessToken) {
        try {
            System.out.println(accessToken);
            System.out.println(jwtTokenProvider.validateToken(accessToken));
            if (!jwtTokenProvider.validateToken(accessToken)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("invalid refresh token"));
            }

            String userEmail = jwtTokenProvider.getEmail(accessToken);
            User user = userRepository.findByEmail(userEmail).orElseThrow(
                    () -> new InvalidRequestStateException("user does not exist")
            );

            if (!userRepository.existsById(user.userId)) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.fail("user does not exist"));
            }

            userRefreshTokenRepository.deleteById(user.userId);
            userRepository.deleteById(user.userId);

            return ResponseEntity
                    .ok(ApiResponse.success(user));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            ApiResponse<Void> body = ApiResponse.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(body);
        }
    }
}
