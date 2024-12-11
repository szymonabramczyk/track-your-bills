package com.example.trackyourbills.services;

import com.example.trackyourbills.dto.BudgetTransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BudgetTransactionService {
    BudgetTransactionDTO createTransaction(BudgetTransactionDTO transactionDTO);

    BudgetTransactionDTO getTransactionById(Long id);

    List<BudgetTransactionDTO> getAllTransactions();

    BudgetTransactionDTO updateTransaction(Long id, BudgetTransactionDTO transactionDTO);

    void deleteTransaction(Long id);

    void setCategoryToNullForTransactions(Long categoryId);

    List<BudgetTransactionDTO> filterTransactions(LocalDate startDate, LocalDate endDate,
                                                         Long categoryId, String type,
                                                         BigDecimal minValue, BigDecimal maxValue);
}
