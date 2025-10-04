package com.BitByBit.ExpenSR.repository;

import com.BitByBit.ExpenSR.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
