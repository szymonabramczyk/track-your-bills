package com.example.trackyourbills.repositories;

import com.example.trackyourbills.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
