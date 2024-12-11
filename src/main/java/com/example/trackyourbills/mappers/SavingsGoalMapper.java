package com.example.trackyourbills.mappers;

import com.example.trackyourbills.dto.SavingsGoalDTO;
import com.example.trackyourbills.entities.SavingsGoal;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.exceptions.ResourceNotFoundException;
import com.example.trackyourbills.repositories.UserRepository;

public class SavingsGoalMapper {
    public static SavingsGoalDTO toDto(SavingsGoal savingsGoal) {
        SavingsGoalDTO dto = new SavingsGoalDTO();
        dto.setId(savingsGoal.getId());
        dto.setName(savingsGoal.getName());
        dto.setTargetValue(savingsGoal.getTargetValue());
        dto.setTargetDate(savingsGoal.getTargetDate());
        dto.setUserId(savingsGoal.getUser().getId());
        return dto;
    }

    public static SavingsGoal toEntity(SavingsGoalDTO dto) {
        SavingsGoal savingsGoal = new SavingsGoal();
        savingsGoal.setId(dto.getId());
        savingsGoal.setName(dto.getName());
        savingsGoal.setTargetValue(dto.getTargetValue());
        savingsGoal.setTargetDate(dto.getTargetDate());
        return savingsGoal;
    }
}
