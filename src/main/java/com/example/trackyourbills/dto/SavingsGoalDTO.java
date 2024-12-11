package com.example.trackyourbills.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsGoalDTO {

    private Long id;
    private String name;
    private double targetValue;
    private LocalDate targetDate;
    private Long userId;
}
