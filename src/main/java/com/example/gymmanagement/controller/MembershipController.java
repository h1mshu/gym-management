package com.example.gymmanagement.controller;

import com.example.gymmanagement.dto.MembershipRequest;
import com.example.gymmanagement.dto.MembershipResponse;
import com.example.gymmanagement.service.MembershipService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping
    public ResponseEntity<MembershipResponse> create(@Valid @RequestBody MembershipRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membershipService.create(request));
    }

    @GetMapping
    public List<MembershipResponse> findAll() {
        return membershipService.findAll();
    }

    @GetMapping("/{id}")
    public MembershipResponse findById(@PathVariable Long id) {
        return membershipService.findById(id);
    }

    @PutMapping("/{id}")
    public MembershipResponse update(@PathVariable Long id, @Valid @RequestBody MembershipRequest request) {
        return membershipService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        membershipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
