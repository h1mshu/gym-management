package com.example.gymmanagement.dto;

import com.example.gymmanagement.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record MemberRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @Pattern(regexp = "^[0-9+() .-]{7,20}$", message = "must be a valid phone number") String phone,
        @NotNull @Min(1) Integer age,
        @NotNull Gender gender,
        @NotNull @PastOrPresent LocalDate joinDate) {
}
