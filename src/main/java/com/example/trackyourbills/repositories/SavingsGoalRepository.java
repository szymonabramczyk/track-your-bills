package com.example.trackyourbills.repositories;

import com.example.trackyourbills.entities.Category;
import com.example.trackyourbills.entities.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    List<SavingsGoal> findByUserId(Long userId);
}
