package com.example.ppompai.server.common.repository;

import com.example.ppompai.server.common.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
