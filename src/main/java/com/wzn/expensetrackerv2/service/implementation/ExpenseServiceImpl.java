package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpensesRepository expensesRepository, MonthRepository monthRepository, CategoryRepository categoryRepository) {
        this.expensesRepository = expensesRepository;
        this.monthRepository = monthRepository;
        this.categoryRepository = categoryRepository;
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

        Optional<Category> category = categoryRepository.findCategoryByName(expense.getCategory().getName());
        category.ifPresent(expense::setCategory);
        if (category.isEmpty()) {
            expense.setCategory(null);
        }

        expensesRepository.save(expense);
        month.addExpense(expense); // Bi-directional (See month entity)
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
