package com.example.ppompai.server.auth.domain;

import com.example.ppompai.server.common.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class UserRefreshToken {
    @Id
    private Long id;

    @OneToOne()
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "refresh_token", length = 512, nullable = false)
    private String refreshToken;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}