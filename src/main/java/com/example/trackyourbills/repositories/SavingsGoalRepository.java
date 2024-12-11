package com.example.trackyourbills.repositories;

import com.example.trackyourbills.entities.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
}
