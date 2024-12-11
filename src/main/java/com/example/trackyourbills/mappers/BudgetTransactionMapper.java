package com.example.trackyourbills.mappers;

import com.example.trackyourbills.dto.BudgetTransactionDTO;
import com.example.trackyourbills.entities.BudgetTransaction;
import com.example.trackyourbills.enums.BudgetTransactionType;

public class BudgetTransactionMapper {
    public static BudgetTransactionDTO toDto(BudgetTransaction budgetTransaction) {
        BudgetTransactionDTO dto = new BudgetTransactionDTO();
        dto.setId(budgetTransaction.getId());
        dto.setValue(budgetTransaction.getValue());
        dto.setDate(budgetTransaction.getDate());
        dto.setType(budgetTransaction.getType().name());

        // Przypisanie userId (może być null, jeśli użytkownik nie istnieje)
        if (budgetTransaction.getUser() != null) {
            dto.setUserId(budgetTransaction.getUser().getId());
        }

        // Przypisanie categoryId (może być null, jeśli kategoria nie istnieje)
        if (budgetTransaction.getCategory() != null) {
            dto.setCategoryId(budgetTransaction.getCategory().getId());
        }

        return dto;
    }

    public static BudgetTransaction toEntity(BudgetTransactionDTO dto) {
        BudgetTransaction budgetTransaction = new BudgetTransaction();
        budgetTransaction.setId(dto.getId());
        budgetTransaction.setValue(dto.getValue());
        budgetTransaction.setDate(dto.getDate());
        budgetTransaction.setType(BudgetTransactionType.valueOf(dto.getType()));

        // Ustawienie użytkownika - zostaw to puste, jeśli logika obsługi użytkowników jest bardziej złożona
        // budżet transakcji może być przypisany do użytkownika później

        return budgetTransaction;
    }
}
