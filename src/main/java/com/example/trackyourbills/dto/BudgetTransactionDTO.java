package com.example.trackyourbills.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetTransactionDTO {
    private Long id;
    private BigDecimal value;
    private LocalDate date;
    private String type; // changed to String for simplicity in API
    private Long userId;
    private Long categoryId;
}
