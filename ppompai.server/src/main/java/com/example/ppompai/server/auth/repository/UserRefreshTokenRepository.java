package com.example.ppompai.server.auth.repository;

import com.example.ppompai.server.auth.domain.UserRefreshToken;
import com.example.ppompai.server.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    Optional<UserRefreshToken> findByUser(User user);
}
