package com.example.gymmanagement.dto;

import com.example.gymmanagement.entity.MembershipStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record MembershipRequest(
        @NotNull @Positive Long memberId,
        @NotBlank String plan,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull MembershipStatus status) {
}
