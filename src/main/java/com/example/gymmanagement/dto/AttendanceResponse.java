package com.example.gymmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponse(Long id, Long memberId, String memberName, LocalDate attendanceDate, LocalTime checkInTime) {
}
