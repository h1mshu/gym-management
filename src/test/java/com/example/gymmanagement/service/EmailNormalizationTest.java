package com.example.gymmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.gymmanagement.dto.MemberRequest;
import com.example.gymmanagement.dto.TrainerRequest;
import com.example.gymmanagement.entity.Gender;
import com.example.gymmanagement.entity.Member;
import com.example.gymmanagement.entity.Trainer;
import com.example.gymmanagement.repository.MemberRepository;
import com.example.gymmanagement.repository.TrainerRepository;
import java.time.LocalDate;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class EmailNormalizationTest {

    @Test
    void normalizesMemberEmailIndependentOfDefaultLocale() {
        Locale originalLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            MemberRepository repository = mock(MemberRepository.class);
            when(repository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

            MemberService service = new MemberService(repository);
            String email = service.create(new MemberRequest("Member", "HIMANSHU@example.com", null, 19, Gender.MALE,
                    LocalDate.of(2026, 1, 1))).email();

            assertEquals("himanshu@example.com", email);
            verify(repository).existsByEmail("himanshu@example.com");
        } finally {
            Locale.setDefault(originalLocale);
        }
    }

    @Test
    void normalizesTrainerEmailIndependentOfDefaultLocale() {
        Locale originalLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            TrainerRepository repository = mock(TrainerRepository.class);
            when(repository.save(any(Trainer.class))).thenAnswer(invocation -> invocation.getArgument(0));

            TrainerService service = new TrainerService(repository);
            String email = service.create(new TrainerRequest("Trainer", "HIMANSHU@example.com", null, "Strength"))
                    .email();

            assertEquals("himanshu@example.com", email);
            verify(repository).existsByEmail("himanshu@example.com");
        } finally {
            Locale.setDefault(originalLocale);
        }
    }
}
