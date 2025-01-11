package com.example.trackyourbills.services.impl;

import com.example.trackyourbills.dto.BudgetTransactionDTO;
import com.example.trackyourbills.entities.BudgetTransaction;
import com.example.trackyourbills.entities.Category;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.enums.BudgetTransactionType;
import com.example.trackyourbills.exceptions.ResourceNotFoundException;
import com.example.trackyourbills.mappers.BudgetTransactionMapper;
import com.example.trackyourbills.repositories.BudgetTransactionRepository;
import com.example.trackyourbills.repositories.CategoryRepository;
import com.example.trackyourbills.repositories.UserRepository;
import com.example.trackyourbills.services.BudgetTransactionService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BudgetTransactionServiceImpl implements BudgetTransactionService {

    private final BudgetTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BudgetTransactionDTO createTransaction(BudgetTransactionDTO transactionDTO) {
        BudgetTransaction transaction = BudgetTransactionMapper.toEntity(transactionDTO);
        System.out.println(transactionDTO.getCategoryId());
        System.out.println(transaction.getCategory());

        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + transactionDTO.getUserId()));
        transaction.setUser(user);

        if (transactionDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + transactionDTO.getCategoryId()));
            transaction.setCategory(category);
        }

        BudgetTransaction savedTransaction = transactionRepository.save(transaction);
        return BudgetTransactionMapper.toDto(savedTransaction);
    }

    @Override
    public BudgetTransactionDTO getTransactionById(Long id) {
        BudgetTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));
        return BudgetTransactionMapper.toDto(transaction);
    }

    @Override
    public List<BudgetTransactionDTO> getAllTransactionsForUser(Long userId) {
        List<BudgetTransaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(BudgetTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetTransactionDTO updateTransaction(Long id, BudgetTransactionDTO transactionDTO) {
        BudgetTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));

        transaction.setValue(transactionDTO.getValue());
        transaction.setDate(transactionDTO.getDate());
        transaction.setType(BudgetTransactionType.valueOf(transactionDTO.getType()));

        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + transactionDTO.getUserId()));
        transaction.setUser(user);

        if (transactionDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + transactionDTO.getCategoryId()));
            transaction.setCategory(category);
        }

        BudgetTransaction updatedTransaction = transactionRepository.save(transaction);
        return BudgetTransactionMapper.toDto(updatedTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        BudgetTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));
        transactionRepository.delete(transaction);
    }

    @Override
    public void setCategoryToNullForTransactions(Long categoryId) {
        List<BudgetTransaction> transactions = transactionRepository.findByCategoryId(categoryId);
        for (BudgetTransaction transaction : transactions) {
            transaction.setCategory(null);
        }
        transactionRepository.saveAll(transactions);
    }


    @Override
    public List<BudgetTransactionDTO> filterTransactionsForUser(Long userId, LocalDate startDate, LocalDate endDate,
                                                         Long categoryId, String type,
                                                         BigDecimal minValue, BigDecimal maxValue) {
        // Użycie JpaSpecificationExecutor do filtrowania
        System.out.println("Filtering transactions with criteria: startDate=" + startDate);

        List<BudgetTransaction> filteredTransactions = transactionRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));

            // Filtruj po dacie
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
            }

            // Filtruj po kategorii
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            // Filtruj po typie transakcji
            if (type != null) {
                try {
                    BudgetTransactionType transactionType = BudgetTransactionType.valueOf(type);
                    predicates.add(criteriaBuilder.equal(root.get("type"), transactionType));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid transaction type: " + type);
                }
            }

            // Filtruj po wartości
            if (minValue != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("value"), minValue));
            }
            if (maxValue != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("value"), maxValue));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        // Mapowanie wyników na DTO
        return filteredTransactions.stream()
                .map(BudgetTransactionMapper::toDto)
                .collect(Collectors.toList());
    }


}
