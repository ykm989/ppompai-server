package com.example.ppompai.server.payment.domain;

import com.example.ppompai.server.common.domain.Group;
import com.example.ppompai.server.common.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.util.List;

public class CreatePaymentRequestDto {
    public Long payerId;

    public BigDecimal amount;

    public List<Long> participants;

    public String title;

    public Long groupId;
}