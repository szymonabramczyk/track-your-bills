package com.example.trackyourbills.controllers;

import com.example.trackyourbills.dto.SavingsGoalDTO;
import com.example.trackyourbills.services.JwtService;
import com.example.trackyourbills.services.SavingsGoalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/savings-goals")
@AllArgsConstructor
public class SavingsGoalController {

    private final SavingsGoalService savingsGoalService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<SavingsGoalDTO> createSavingsGoal(@RequestBody SavingsGoalDTO savingsGoalDTO) {
        SavingsGoalDTO createdSavingsGoal = savingsGoalService.createSavingsGoal(savingsGoalDTO);
        return new ResponseEntity<>(createdSavingsGoal, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingsGoalDTO> getSavingsGoalById(@PathVariable Long id) {
        SavingsGoalDTO savingsGoal = savingsGoalService.getSavingsGoalById(id);
        return ResponseEntity.ok(savingsGoal);
    }

    @GetMapping
    public ResponseEntity<List<SavingsGoalDTO>> getAllSavingsGoalsForUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);
        List<SavingsGoalDTO> savingsGoals = savingsGoalService.getAllSavingsGoalsForUser(userId);
        return ResponseEntity.ok(savingsGoals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavingsGoalDTO> updateSavingsGoal(
            @PathVariable Long id,
            @RequestBody SavingsGoalDTO savingsGoalDTO) {
        SavingsGoalDTO updatedSavingsGoal = savingsGoalService.updateSavingsGoal(id, savingsGoalDTO);
        return ResponseEntity.ok(updatedSavingsGoal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSavingsGoal(@PathVariable Long id) {
        savingsGoalService.deleteSavingsGoal(id);
        return ResponseEntity.ok("Savings goal deleted successfully");
    }
}
