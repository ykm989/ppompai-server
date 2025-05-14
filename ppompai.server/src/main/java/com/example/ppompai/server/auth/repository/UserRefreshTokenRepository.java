package com.example.ppompai.server.auth.repository;

import com.example.ppompai.server.auth.domain.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
}
