package com.example.ppompai.server.common.domain;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Long userId;

    @Column(length = 100, nullable = false, unique = true)
    public String email;

    @Column(length = 100, nullable = false)
    public String password;

    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private List<Group> joinedGroups;

    @CreationTimestamp
    @Column(updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    public LocalDateTime updatedAt;
}
