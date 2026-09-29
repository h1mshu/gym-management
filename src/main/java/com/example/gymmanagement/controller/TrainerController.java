package com.example.gymmanagement.controller;

import com.example.gymmanagement.dto.TrainerRequest;
import com.example.gymmanagement.dto.TrainerResponse;
import com.example.gymmanagement.service.TrainerService;
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
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<TrainerResponse> create(@Valid @RequestBody TrainerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.create(request));
    }

    @GetMapping
    public List<TrainerResponse> findAll() {
        return trainerService.findAll();
    }

    @GetMapping("/{id}")
    public TrainerResponse findById(@PathVariable Long id) {
        return trainerService.findById(id);
    }

    @PutMapping("/{id}")
    public TrainerResponse update(@PathVariable Long id, @Valid @RequestBody TrainerRequest request) {
        return trainerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
