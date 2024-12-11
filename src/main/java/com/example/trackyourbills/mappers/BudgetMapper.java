package com.example.trackyourbills.mappers;

import com.example.trackyourbills.dto.BudgetDTO;
import com.example.trackyourbills.entities.Budget;

public class BudgetMapper {
    public static BudgetDTO toDto(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setValue(budget.getValue());
        dto.setMonth(budget.getMonth());
        dto.setYear(budget.getYear());
        dto.setUserId(budget.getUser().getId());
        return dto;
    }

    public static Budget toEntity(BudgetDTO dto) {
        Budget budget = new Budget();
        budget.setId(dto.getId());
        budget.setValue(dto.getValue());
        budget.setMonth(dto.getMonth());
        budget.setYear(dto.getYear());

        return budget;
    }
}

