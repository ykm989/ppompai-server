package com.example.ppompai.server.common.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "settlements")
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Payment payment;

    @OneToOne
    private User payer;

    @OneToOne
    private User spender;

    @Column
    private Number amount;
}
