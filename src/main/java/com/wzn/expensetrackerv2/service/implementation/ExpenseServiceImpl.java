package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.repository.ExpensesRepository;
import com.wzn.expensetrackerv2.repository.MonthRepository;
import com.wzn.expensetrackerv2.service.ExpenseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// TODO: Perhapsss an update expenses?

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpensesRepository expensesRepository;
    private final MonthRepository monthRepository;

    public ExpenseServiceImpl(ExpensesRepository expensesRepository, MonthRepository monthRepository) {
        this.expensesRepository = expensesRepository;
        this.monthRepository = monthRepository;
    }


    @Override
    @Transactional
    public Expense createExpense(Month month, Expense expense) {
        if (month == null) {
            throw new IllegalArgumentException("Month cannot be null");
        }
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        expensesRepository.save(expense);
        month.addExpense(expense); // Assume addExpense handles the relationship correctly
        return expense;
    }


    @Override
    @Transactional
    public boolean deleteExpense(Long id) {
        Optional<Expense> expenseToDelete = expensesRepository.findById(id);
        if (expenseToDelete.isEmpty()) {
            return false; // Simply return false if the expense is not found
        }

        // Find the associated month and proceed with deletion if found
        expenseToDelete.ifPresent(expense -> {
            monthRepository.findById(expense.getMonth().getId()).ifPresent(month -> {
                month.removeExpense(expense);
                expensesRepository.delete(expense);
            });
        });

        return true; // Return true if the deletion process is initiated
    }


    @Override
    public Optional<Expense> findExpenseById(Long id) {
        return expensesRepository.findById(id);
    }


    @Override
    @Transactional
    public List<Expense> findAllExpenses() {
        return expensesRepository.findAll();
    }
}
