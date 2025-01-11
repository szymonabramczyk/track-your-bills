package com.example.trackyourbills.repositories;

import com.example.trackyourbills.entities.Budget;
import com.example.trackyourbills.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId);
    Optional<Budget> findByUserIdAndMonthAndYear(Long userId, String month, int year);
    @Query("SELECT DISTINCT b.year, b.month FROM Budget b WHERE b.user.id = :userId")
    List<Object[]> findAvailableYearsAndMonthsForUser(@Param("userId") Long userId);

}
