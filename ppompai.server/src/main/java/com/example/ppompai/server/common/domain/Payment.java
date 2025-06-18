package com.example.ppompai.server.common.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @Column(nullable = false)
    private Number amount;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Settlement> settlements;

    @Column(length = 255)
    private String title;

    @ManyToOne
    public Group group;

    @CreationTimestamp
    @Column(updatable = false)
    public LocalDateTime createdAt;
}