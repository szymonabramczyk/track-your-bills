package com.example.trackyourbills.services.impl;

import com.example.trackyourbills.dto.BudgetDTO;
import com.example.trackyourbills.dto.BudgetReportDTO;
import com.example.trackyourbills.entities.Budget;
import com.example.trackyourbills.entities.BudgetTransaction;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.enums.BudgetTransactionType;
import com.example.trackyourbills.exceptions.ResourceNotFoundException;
import com.example.trackyourbills.mappers.BudgetMapper;
import com.example.trackyourbills.repositories.BudgetRepository;
import com.example.trackyourbills.repositories.BudgetTransactionRepository;
import com.example.trackyourbills.repositories.UserRepository;
import com.example.trackyourbills.services.BudgetService;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final BudgetTransactionRepository transactionRepository;

    @Override
    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        Budget budget = BudgetMapper.toEntity(budgetDTO);

        User user = findUserById(budgetDTO.getUserId());
        budget.setUser(user);

        Budget savedBudget = this.budgetRepository.save(budget);

        return BudgetMapper.toDto(savedBudget);
    }

    @Override
    public BudgetDTO getBudgetById(Long id) {
        Budget budget = this.budgetRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Budget not found with id: " + id));
        return BudgetMapper.toDto(budget);
    }

    @Override
    public List<BudgetDTO> getAllBudgetsForUser(Long userId) {
        List<Budget> budgets = this.budgetRepository.findByUserId(userId);
        return budgets
                .stream()
                .map(BudgetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO) {
        Budget budget = this.budgetRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Budget not found with ID: " + id));

        budget.setValue(budgetDTO.getValue());
        budget.setMonth(budgetDTO.getMonth());
        budget.setYear(budgetDTO.getYear());

        User user = findUserById(budgetDTO.getUserId());
        budget.setUser(user);

        Budget updatedBudget = budgetRepository.save(budget);

        return BudgetMapper.toDto(updatedBudget);
    }

    @Override
    public void deleteBudget(Long id) {
        Budget budget = this.budgetRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Budget not found with ID: " + id));

        this.budgetRepository.delete(budget);
    }

    private User findUserById(Long userID) {
        return userRepository.findById(userID)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + userID));
    }

    private int getMonthNumber(String monthName) {
        return Month.valueOf(monthName.toUpperCase()).getValue();
    }

    @Override
    public BudgetReportDTO generateBudgetReport(String month, int year, String type, Long userId) {
        Budget budget = budgetRepository.findByUserIdAndMonthAndYear(userId, month, year)
                .orElseThrow(() -> new RuntimeException("No budget found for the specified month and year"));

        // Pobierz transakcje z odpowiednimi filtrami
        List<BudgetTransaction> transactions = transactionRepository.findAllByUserIdAndDateMonthAndYearAndType(
                userId, getMonthNumber(month), year, type != null ? BudgetTransactionType.valueOf(type) : null
        );
        System.out.println(transactions.get(0).getValue());

        // Oblicz sumy dla dochodów i wydatków
        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getType() == BudgetTransactionType.INCOME)
                .map(BudgetTransaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> t.getType() == BudgetTransactionType.EXPENSE)
                .map(BudgetTransaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Podział na kategorie
        Map<String, BigDecimal> categoryBreakdown = new HashMap<>();
            transactions.forEach(t -> {
            String categoryName = t.getCategory() != null ? t.getCategory().getName() : "Unspecified";
            categoryBreakdown.put(categoryName, categoryBreakdown.getOrDefault(categoryName, BigDecimal.ZERO).add(t.getValue()));
        });

        System.out.println("Total expense: " + totalExpense);
        System.out.println("Total budget value: " + budget.getValue());


        // Sprawdzenie, czy budżet został przekroczony
        boolean isOverBudget = totalExpense.compareTo(BigDecimal.valueOf(budget.getValue())) > 0;

        System.out.println(isOverBudget);
        // Zwróć DTO
        return new BudgetReportDTO(totalIncome, totalExpense, categoryBreakdown, BigDecimal.valueOf(budget.getValue()), isOverBudget);
    }

    public List<Map<String, Object>> getAvailableBudgets(Long userId) {
        List<Object[]> results = budgetRepository.findAvailableYearsAndMonthsForUser(userId);
        List<Map<String, Object>> budgets = results.stream().map(row -> {
            Map<String, Object> budget = new HashMap<>();
            budget.put("year", row[0]);
            budget.put("month", row[1]);
            return budget;
        }).collect(Collectors.toList());

        return budgets;
    }

}
