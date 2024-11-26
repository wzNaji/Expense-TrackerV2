package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.repository.ExpensesRepository;
import com.wzn.expensetrackerv2.service.ExpenseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TODO: Perhapsss an update expenses?

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpensesRepository expensesRepository;

    public ExpenseServiceImpl(ExpensesRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }


    @Override
    @Transactional
    public Expense createExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("'Expense' was not found");
        }
        try {
            expensesRepository.save(expense);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong. 'Expense' was not saved");
        }
        return expense;
    }

    @Override
    @Transactional
    public boolean deleteExpense(Long id) {
        try {
            expensesRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete expense with ID " + id, e);
        }
    }

    @Override
    public Expense findExpenseById(Long id) {
        return expensesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense with ID " + id + " was not found."));
    }

    @Override
    @Transactional
    public List<Expense> findAllExpenses() {
        List<Expense> expenses = expensesRepository.findAll();
        if (expenses.isEmpty()) {
            throw new RuntimeException("No expenses found");
        }
        return expenses;
    }
}
