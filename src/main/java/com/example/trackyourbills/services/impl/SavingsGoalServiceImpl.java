package com.example.trackyourbills.services.impl;

import com.example.trackyourbills.dto.SavingsGoalDTO;
import com.example.trackyourbills.entities.SavingsGoal;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.exceptions.ResourceNotFoundException;
import com.example.trackyourbills.mappers.SavingsGoalMapper;
import com.example.trackyourbills.repositories.SavingsGoalRepository;
import com.example.trackyourbills.repositories.UserRepository;
import com.example.trackyourbills.services.SavingsGoalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SavingsGoalServiceImpl implements SavingsGoalService {

    private final SavingsGoalRepository savingsGoalRepository;
    private final UserRepository userRepository;

    @Override
    public SavingsGoalDTO createSavingsGoal(SavingsGoalDTO savingsGoalDTO) {
        SavingsGoal savingsGoal = SavingsGoalMapper.toEntity(savingsGoalDTO);

        User user = findUserById(savingsGoalDTO.getUserId());
        savingsGoal.setUser(user);

        SavingsGoal savedSavingsGoal = savingsGoalRepository.save(savingsGoal);
        return SavingsGoalMapper.toDto(savedSavingsGoal);
    }

    @Override
    public SavingsGoalDTO getSavingsGoalById(Long id) {
        SavingsGoal savingsGoal = savingsGoalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Savings Goal not found with ID: " + id));
        return SavingsGoalMapper.toDto(savingsGoal);
    }

    @Override
    public List<SavingsGoalDTO> getAllSavingsGoals() {
        return savingsGoalRepository.findAll().stream()
                .map(SavingsGoalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SavingsGoalDTO updateSavingsGoal(Long id, SavingsGoalDTO savingsGoalDTO) {
        SavingsGoal savingsGoal = savingsGoalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Savings Goal not found with ID: " + id));

        savingsGoal.setName(savingsGoalDTO.getName());
        savingsGoal.setTargetValue(savingsGoalDTO.getTargetValue());
        savingsGoal.setTargetDate(savingsGoalDTO.getTargetDate());

        User user = findUserById(savingsGoalDTO.getUserId());
        savingsGoal.setUser(user);

        SavingsGoal updatedSavingsGoal = savingsGoalRepository.save(savingsGoal);
        return SavingsGoalMapper.toDto(updatedSavingsGoal);
    }

    @Override
    public void deleteSavingsGoal(Long id) {
        SavingsGoal savingsGoal = savingsGoalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Savings Goal not found with ID: " + id));
        savingsGoalRepository.delete(savingsGoal);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + userId));
    }
}
