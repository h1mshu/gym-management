package com.example.gymmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TrainerRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @Pattern(regexp = "^[0-9+() .-]{7,20}$", message = "must be a valid phone number") String phone,
        @NotBlank String specialization) {
}
