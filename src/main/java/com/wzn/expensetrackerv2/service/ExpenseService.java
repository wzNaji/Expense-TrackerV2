package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    Expense createExpense(Month month, Expense expense);
    boolean deleteExpense(Long id);
    Optional<Expense> findExpenseById(Long id);
    List<Expense> findAllExpenses();

}
