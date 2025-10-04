package com.BitByBit.ExpenSR.controller;

import com.BitByBit.ExpenSR.dto.request.CreateExpenseRequest;
import com.BitByBit.ExpenSR.entity.*;
import com.BitByBit.ExpenSR.exception.*;
import com.BitByBit.ExpenSR.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@Validated
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    @Cacheable(value = "expenses", key = "'all'")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "expense", key = "#id")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return expenseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    }

    @PostMapping
    @CacheEvict(value = "expenses", allEntries = true)
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody CreateExpenseRequest request) {
        Expense expense = mapToEntity(request);
        Expense savedExpense = expenseRepository.save(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = {"expense", "expenses"}, key = "#id", allEntries = true)
    public ResponseEntity<Expense> updateExpense(
            @PathVariable Long id, 
            @Valid @RequestBody CreateExpenseRequest request) {
        
        return expenseRepository.findById(id)
                .map(expense -> {
                    updateEntityFromRequest(expense, request);
                    return ResponseEntity.ok(expenseRepository.save(expense));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"expense", "expenses"}, key = "#id", allEntries = true)
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Expense mapToEntity(CreateExpenseRequest request) {
        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getDate());
        return expense;
    }

    private void updateEntityFromRequest(Expense expense, CreateExpenseRequest request) {
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getDate());
    }
}
