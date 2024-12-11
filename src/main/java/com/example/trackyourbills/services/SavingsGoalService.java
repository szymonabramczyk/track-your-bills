package com.example.trackyourbills.services;

import com.example.trackyourbills.dto.SavingsGoalDTO;

import java.util.List;

public interface SavingsGoalService {
    SavingsGoalDTO createSavingsGoal(SavingsGoalDTO savingsGoalDTO);
    SavingsGoalDTO getSavingsGoalById(Long id);
    List<SavingsGoalDTO> getAllSavingsGoals();
    SavingsGoalDTO updateSavingsGoal(Long id, SavingsGoalDTO savingsGoalDTO);
    void deleteSavingsGoal(Long id);
}
