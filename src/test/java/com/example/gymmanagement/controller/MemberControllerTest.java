package com.example.gymmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.gymmanagement.dto.MemberRequest;
import com.example.gymmanagement.dto.MemberResponse;
import com.example.gymmanagement.entity.Gender;
import com.example.gymmanagement.exception.GlobalExceptionHandler;
import com.example.gymmanagement.exception.ResourceNotFoundException;
import com.example.gymmanagement.service.MemberService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

@WebMvcTest(MemberController.class)
@Import(GlobalExceptionHandler.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @Test
    void createsMemberAndReturnsCreatedStatus() throws Exception {
        when(memberService.create(any(MemberRequest.class))).thenReturn(
                new MemberResponse(1L, "Himanshu", "himanshu@example.com", "9876543210",
                        19, Gender.MALE, LocalDate.of(2026, 1, 1)));

        mockMvc.perform(post("/api/members")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "Himanshu",
                                  "email": "himanshu@example.com",
                                  "phone": "9876543210",
                                  "age": 19,
                                  "gender": "MALE",
                                  "joinDate": "2026-01-01"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.gender").value("MALE"));
    }

    @Test
    void rejectsInvalidMemberRequest() throws Exception {
        mockMvc.perform(post("/api/members")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "",
                                  "email": "not-an-email",
                                  "age": 0,
                                  "gender": "MALE",
                                  "joinDate": "2030-01-01"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void returnsNotFoundForUnknownMember() throws Exception {
        when(memberService.findById(99L)).thenThrow(new ResourceNotFoundException("Member", 99L));

        mockMvc.perform(get("/api/members/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Member not found with id 99"));
    }
}
