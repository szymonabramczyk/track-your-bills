package com.example.trackyourbills.repositories;

import com.example.trackyourbills.entities.BudgetTransaction;
import com.example.trackyourbills.enums.BudgetTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetTransactionRepository extends JpaRepository<BudgetTransaction, Long>, JpaSpecificationExecutor<BudgetTransaction> {
    List<BudgetTransaction> findByCategoryId(Long categoryId);
    List<BudgetTransaction> findByUserId(Long userId);
    @Query("SELECT bt FROM BudgetTransaction bt WHERE bt.user.id = :userId AND " +
            "EXTRACT(MONTH FROM bt.date) = :dateMonth AND EXTRACT(YEAR FROM bt.date) = :dateYear AND " +
            "(:type IS NULL OR bt.type = :type)")
    List<BudgetTransaction> findAllByUserIdAndDateMonthAndYearAndType(
            @Param("userId") Long userId,
            @Param("dateMonth") int dateMonth,
            @Param("dateYear") int dateYear,
            @Param("type") BudgetTransactionType type);




}
