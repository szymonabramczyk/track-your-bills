package com.example.trackyourbills.services.impl;

import com.example.trackyourbills.dto.BudgetDTO;
import com.example.trackyourbills.entities.Budget;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.exceptions.ResourceNotFoundException;
import com.example.trackyourbills.mappers.BudgetMapper;
import com.example.trackyourbills.repositories.BudgetRepository;
import com.example.trackyourbills.repositories.UserRepository;
import com.example.trackyourbills.services.BudgetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

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
    public List<BudgetDTO> getAllBudgets() {
        List<Budget> budgets = this.budgetRepository.findAll();
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
}
