package com.example.ppompai.server.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRefreshToken {
    @Id
    private Long id;

    @Column(length = 512, nullable = false)
    private String refreshToken;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}