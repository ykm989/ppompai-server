package com.example.ppompai.server.payment.controller;

import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.Payment;
import com.example.ppompai.server.common.domain.User;
import com.example.ppompai.server.payment.domain.CreatePaymentRequestDto;
import com.example.ppompai.server.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public final class PaymentController {
    private PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> payment(
            @RequestBody CreatePaymentRequestDto request,
            @RequestAttribute User user
    ) {
        return paymentService.createPayment(request, user);
    }
}