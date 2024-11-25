package com.wzn.expensetrackerv2.repository;

import com.wzn.expensetrackerv2.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<Expense, Long> {
}
