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
        if (expense == null || month == null) {
            throw new IllegalArgumentException("'Expense' or 'Month' was not found");
        }
        try {
            expensesRepository.save(expense);
            month.addExpense(expense);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong. 'Expense' was not saved");
        }
        return expense;
    }

    @Override
    @Transactional
    public boolean deleteExpense(Long id) {
        Optional<Expense> expenseToDelete = expensesRepository.findById(id);

        if (expenseToDelete.isEmpty()) {
            throw new IllegalArgumentException("Expense with ID " + id + " was not found.");
        }

        // Proceed only if the expense is present
        Optional<Month> associatedMonth = monthRepository.findById(expenseToDelete.get().getMonth().getId());

        if (associatedMonth.isEmpty()) {
            throw new IllegalArgumentException("Associated Month was not found.");
        }
        // At this point, both expenseToDelete and associatedMonth are present, proceed with the logic

        try {
            expensesRepository.deleteById(id);
            associatedMonth.get().removeExpense(expenseToDelete.get());
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
