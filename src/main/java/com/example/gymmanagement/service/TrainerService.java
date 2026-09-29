package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.TrainerRequest;
import com.example.gymmanagement.dto.TrainerResponse;
import com.example.gymmanagement.entity.Trainer;
import com.example.gymmanagement.exception.DuplicateResourceException;
import com.example.gymmanagement.exception.ResourceNotFoundException;
import com.example.gymmanagement.repository.TrainerRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public TrainerResponse create(TrainerRequest request) {
        if (trainerRepository.existsByEmail(request.email().trim().toLowerCase())) {
            throw new DuplicateResourceException("A trainer with this email already exists");
        }
        Trainer trainer = new Trainer();
        apply(trainer, request);
        return response(trainerRepository.save(trainer));
    }

    @Transactional(readOnly = true)
    public List<TrainerResponse> findAll() {
        return trainerRepository.findAll().stream().map(this::response).toList();
    }

    @Transactional(readOnly = true)
    public TrainerResponse findById(Long id) {
        return response(getTrainer(id));
    }

    public TrainerResponse update(Long id, TrainerRequest request) {
        Trainer trainer = getTrainer(id);
        if (trainerRepository.existsByEmailAndIdNot(request.email().trim().toLowerCase(), id)) {
            throw new DuplicateResourceException("A trainer with this email already exists");
        }
        apply(trainer, request);
        return response(trainerRepository.save(trainer));
    }

    public void delete(Long id) {
        trainerRepository.delete(getTrainer(id));
    }

    private Trainer getTrainer(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer", id));
    }

    private void apply(Trainer trainer, TrainerRequest request) {
        trainer.setName(request.name().trim());
        trainer.setEmail(request.email().trim().toLowerCase());
        trainer.setPhone(request.phone());
        trainer.setSpecialization(request.specialization().trim());
    }

    private TrainerResponse response(Trainer trainer) {
        return new TrainerResponse(trainer.getId(), trainer.getName(), trainer.getEmail(),
                trainer.getPhone(), trainer.getSpecialization());
    }
}
