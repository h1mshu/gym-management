package com.example.gymmanagement.dto;

import com.example.gymmanagement.entity.Gender;
import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String name,
        String email,
        String phone,
        Integer age,
        Gender gender,
        LocalDate joinDate) {
}
