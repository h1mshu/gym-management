package com.example.gymmanagement.dto;

import com.example.gymmanagement.entity.MembershipStatus;
import java.time.LocalDate;

public record MembershipResponse(
        Long id,
        Long memberId,
        String memberName,
        String plan,
        LocalDate startDate,
        LocalDate endDate,
        MembershipStatus status) {
}
