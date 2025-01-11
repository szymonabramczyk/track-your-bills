package com.example.trackyourbills.controllers;

import com.example.trackyourbills.dto.BudgetTransactionDTO;
import com.example.trackyourbills.services.BudgetTransactionService;
import com.example.trackyourbills.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class BudgetTransactionController {

    private final BudgetTransactionService transactionService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<BudgetTransactionDTO> createTransaction(@RequestBody BudgetTransactionDTO transactionDTO) {
        BudgetTransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetTransactionDTO> getTransactionById(@PathVariable Long id) {
        BudgetTransactionDTO transactionDTO = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDTO);
    }

    @GetMapping
    public ResponseEntity<List<BudgetTransactionDTO>> getAllTransactionsForUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);
        List<BudgetTransactionDTO> transactions = transactionService.getAllTransactionsForUser(userId);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetTransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody BudgetTransactionDTO transactionDTO) {
        BudgetTransactionDTO updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BudgetTransactionDTO>> filterTransactionsForUser(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String type, // INCOME or EXPENSE
            @RequestParam(required = false) BigDecimal minValue,
            @RequestParam(required = false) BigDecimal maxValue) {

        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        List<BudgetTransactionDTO> transactions = transactionService.filterTransactionsForUser(
                userId, startDate, endDate, categoryId, type, minValue, maxValue);

        return ResponseEntity.ok(transactions);
    }

}
