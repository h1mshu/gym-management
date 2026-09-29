package com.example.gymmanagement.dto;

import com.example.gymmanagement.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentRequest(
        @NotNull @Positive Long memberId,
        @NotNull @Positive BigDecimal amount,
        @NotNull LocalDate paymentDate,
        @NotNull PaymentStatus status) {
}
