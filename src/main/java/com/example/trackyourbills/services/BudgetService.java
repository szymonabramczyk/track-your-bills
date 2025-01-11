package com.example.trackyourbills.services;

import com.example.trackyourbills.dto.BudgetDTO;
import com.example.trackyourbills.dto.BudgetReportDTO;

import java.util.List;
import java.util.Map;

public interface BudgetService {
    BudgetDTO createBudget(BudgetDTO budgetDTO);

    BudgetDTO getBudgetById(Long id);

    List<BudgetDTO> getAllBudgetsForUser(Long userId);

    BudgetReportDTO generateBudgetReport(String month, int year, String type, Long userId);

    BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO);

    void deleteBudget(Long id);

    List<Map<String, Object>> getAvailableBudgets(Long userId);
}
