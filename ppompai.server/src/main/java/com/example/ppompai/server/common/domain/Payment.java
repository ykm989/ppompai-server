package com.example.ppompai.server.common.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne
    private User payer;

    @Column
    private Number amount;

    @OneToMany
    private List<User> participants;

    @Column(length = 255)
    private String title;

    @OneToOne
    private Group group;

    @CreationTimestamp
    @Column(updatable = false)
    public LocalDateTime createdAt;
}
