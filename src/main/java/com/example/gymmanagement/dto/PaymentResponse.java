package com.example.gymmanagement.dto;

import com.example.gymmanagement.entity.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponse(Long id, Long memberId, String memberName, BigDecimal amount, LocalDate paymentDate, PaymentStatus status) {
}
