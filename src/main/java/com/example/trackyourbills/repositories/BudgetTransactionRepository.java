package com.example.trackyourbills.repositories;

import com.example.trackyourbills.entities.BudgetTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BudgetTransactionRepository extends JpaRepository<BudgetTransaction, Long>, JpaSpecificationExecutor<BudgetTransaction> {
    List<BudgetTransaction> findByCategoryId(Long categoryId);
}
