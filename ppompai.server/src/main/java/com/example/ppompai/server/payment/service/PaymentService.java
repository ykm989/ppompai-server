package com.example.ppompai.server.payment.service;

import com.example.ppompai.server.auth.repository.UserRepository;
import com.example.ppompai.server.common.ApiResponse;
import com.example.ppompai.server.common.domain.Group;
import com.example.ppompai.server.common.domain.Payment;
import com.example.ppompai.server.common.domain.Settlement;
import com.example.ppompai.server.common.domain.User;
import com.example.ppompai.server.common.repository.GroupRepository;
import com.example.ppompai.server.common.repository.PaymentRepository;
import com.example.ppompai.server.payment.domain.CreatePaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public final class PaymentService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    public ResponseEntity<ApiResponse<?>> createPayment(CreatePaymentRequestDto request, User user) {
        try {
            Group group = groupRepository.findById(request.groupId)
                    .orElseThrow(() -> new IllegalArgumentException("group not found"));

            // 사용자가 그룹에 속할 경우에만 추가 가능
            if(group.getMembers().contains(user) || group.getOwner().equals(user)) {
                User payer = userRepository.findByUserId(request.payerId);

                Payment payment = Payment.builder()
                        .payer(payer)
                        .amount(request.amount)
                        .title(request.title)
                        .group(group)
                        .build();

                // 참여자 일괄 조회
                List<User> participantUsers = userRepository.findAllById(request.participants);

                if (participantUsers.isEmpty()) {
                    throw new IllegalArgumentException(("participant not found"));
                }

                // 정산 금액
                BigDecimal share = request.amount.divide(BigDecimal.valueOf(participantUsers.size()), 2, RoundingMode.HALF_UP);

                // 참여자 수만큼 Settlement 생성
                List<Settlement> settlements = participantUsers.stream()
                        .filter(participant -> !participant.userId.equals(payer.userId))
                        .map(participant -> Settlement.builder()
                                .payment(payment)
                                .payer(payer)
                                .spender(participant)
                                .amount(share)
                                .build()
                        )
                        .toList();

                payment.setSettlements(settlements);
                paymentRepository.save(payment);

                return ResponseEntity.ok(ApiResponse.success(payment));
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
