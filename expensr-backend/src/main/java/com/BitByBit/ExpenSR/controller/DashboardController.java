package com.BitByBit.ExpenSR.controller;

import com.BitByBit.ExpenSR.entity.Expense;
import com.BitByBit.ExpenSR.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/summary")
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        List<Expense> allExpenses = expenseRepository.findAll();
        
        // Total expenses count
        summary.put("totalExpenses", allExpenses.size());
        
        // Total amount (handle BigDecimal)
        BigDecimal totalAmount = allExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        summary.put("totalAmount", totalAmount);
        
        // Average expense
        BigDecimal avgExpense = allExpenses.isEmpty() ? BigDecimal.ZERO : 
                totalAmount.divide(BigDecimal.valueOf(allExpenses.size()), 2, BigDecimal.ROUND_HALF_UP);
        summary.put("averageExpense", avgExpense);
        
        // Recent expenses (last 5) - sort by ID instead of date if no date field
        List<Expense> recentExpenses = allExpenses.stream()
                .sorted((e1, e2) -> Long.compare(e2.getId(), e1.getId()))
                .limit(5)
                .toList();
        summary.put("recentExpenses", recentExpenses);
        
        return summary;
    }

    @GetMapping("/expenses/by-category")
    public Map<String, BigDecimal> getExpensesByCategory() {
        List<Expense> allExpenses = expenseRepository.findAll();
        Map<String, BigDecimal> categoryTotals = new HashMap<>();
        
        for (Expense expense : allExpenses) {
            String category = expense.getCategory();
            categoryTotals.put(category, 
                categoryTotals.getOrDefault(category, BigDecimal.ZERO).add(expense.getAmount()));
        }
        
        return categoryTotals;
    }

    @GetMapping("/expenses/monthly")
    public Map<String, Object> getMonthlyExpenses() {
        Map<String, Object> monthlyData = new HashMap<>();
        // Implementation depends on your date handling requirements
        // This is a placeholder for monthly grouping logic
        monthlyData.put("message", "Monthly expense data endpoint");
        return monthlyData;
    }
}
