package com.example.trackyourbills.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetReportDTO {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private Map<String, BigDecimal> categoryBreakdown; // Mapowanie kategorii na kwoty
    private BigDecimal budgetLimit; // Ustawiony budżet na dany miesiąc
    private boolean isOverBudget; // Czy budżet został przekroczony
}
