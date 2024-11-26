package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.entity.Expense;

import java.util.List;

public interface ExpenseService {

    void createExpense(Expense expense);
    boolean deleteExpense(Long id);
    Expense findExpenseById(Long id);
    List<Expense> findAllExpenses();

}
