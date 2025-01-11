package com.example.trackyourbills.controllers;

import com.example.trackyourbills.dto.BudgetDTO;
import com.example.trackyourbills.dto.BudgetReportDTO;
import com.example.trackyourbills.services.BudgetService;
import com.example.trackyourbills.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/budgets")
@AllArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(@RequestBody BudgetDTO budgetDTO) {
        BudgetDTO createdBudget = budgetService.createBudget(budgetDTO);
        return new ResponseEntity<>(createdBudget, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable Long id) {
        BudgetDTO budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    @GetMapping
    public ResponseEntity<List<BudgetDTO>> getAllBudgetsForUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);
        List<BudgetDTO> budgets = budgetService.getAllBudgetsForUser(userId);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/report")
    public ResponseEntity<BudgetReportDTO> getBudgetReport(
            @RequestParam String month,
            @RequestParam int year,
            @RequestParam(required = false) String type, // Optional: "INCOME" or "EXPENSE"
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);
        BudgetReportDTO report = budgetService.generateBudgetReport(month, year, type, userId);
        return ResponseEntity.ok(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDTO> updateBudget(@PathVariable Long id, @RequestBody BudgetDTO budgetDTO) {
        BudgetDTO updatedBudget = budgetService.updateBudget(id, budgetDTO);
        return ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok("Budget deleted successfully");
    }

    @GetMapping("/available")
    public ResponseEntity<List<Map<String, Object>>> getAvailableBudgets(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);
        List<Map<String, Object>> budgets = budgetService.getAvailableBudgets(userId);
        return ResponseEntity.ok(budgets);
    }

}
