package com.example.gymmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRequest(
        @NotNull @Positive Long memberId,
        @NotNull LocalDate attendanceDate,
        @NotNull LocalTime checkInTime) {
}
