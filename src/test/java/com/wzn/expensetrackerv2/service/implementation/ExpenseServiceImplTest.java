package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.repository.ExpensesRepository;
import com.wzn.expensetrackerv2.repository.MonthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {

    @Mock
    private MonthRepository monthRepository;

    @Mock
    private ExpensesRepository expensesRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createExpense_ShouldCreateAndAssociateExpenseWithMonth() {
        // Arrange
        Month month = new Month();
        month.setId(1L);

        Expense expense = new Expense();
        expense.setPrice(100.0);
        expense.setDescription("Groceries");

        when(expensesRepository.save(expense)).thenReturn(expense);

        // Act
        Expense result = expenseService.createExpense(month, expense);

        // Assert
        assertNotNull(result, "The created expense should not be null");
        verify(expensesRepository, times(1)).save(expense);
        assertTrue(month.getListOfExpenses().contains(expense), "The expense should be associated with the month");
    }

    @Test
    void createExpense_ShouldThrowException_WhenMonthIsNull() {
        // Arrange
        Expense expense = new Expense();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> expenseService.createExpense(null, expense), "Month cannot be null");
    }

    @Test
    void deleteExpense_ShouldReturnTrue_WhenExpenseExists() {
        // Arrange
        Long expenseId = 1L;
        Expense expense = new Expense();
        expense.setId(expenseId);

        Month month = new Month();
        month.setId(1L);
        expense.setMonth(month);
        month.addExpense(expense);

        when(expensesRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(monthRepository.findById(month.getId())).thenReturn(Optional.of(month));

        // Act
        boolean result = expenseService.deleteExpense(expenseId);

        // Assert
        assertTrue(result, "deleteExpense should return true when deletion is successful");
        verify(expensesRepository, times(1)).findById(expenseId);
        verify(monthRepository, times(1)).findById(month.getId());
        verify(expensesRepository, times(1)).delete(expense);
        assertFalse(month.getListOfExpenses().contains(expense), "The expense should be removed from the month");
    }

    @Test
    void deleteExpense_ShouldReturnFalse_WhenExpenseDoesNotExist() {
        // Arrange
        Long expenseId = 1L;
        when(expensesRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act
        boolean result = expenseService.deleteExpense(expenseId);

        // Assert
        assertFalse(result, "deleteExpense should return false when the expense does not exist");
        verify(expensesRepository, times(1)).findById(expenseId);
        verify(monthRepository, never()).findById(any());
    }

    @Test
    void findExpenseById_ShouldReturnExpense_WhenExists() {
        // Arrange
        Long expenseId = 1L;
        Expense expense = new Expense();
        expense.setId(expenseId);
        when(expensesRepository.findById(expenseId)).thenReturn(Optional.of(expense));

        // Act
        Optional<Expense> result = expenseService.findExpenseById(expenseId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expenseId, result.get().getId());
        verify(expensesRepository, times(1)).findById(expenseId);
    }

    @Test
    void findExpenseById_ShouldReturnEmptyOptional_WhenExpenseNotFound() {
        // Arrange
        Long expenseId = 1L;
        when(expensesRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act
        Optional<Expense> result = expenseService.findExpenseById(expenseId);

        // Assert
        assertFalse(result.isPresent());
        verify(expensesRepository, times(1)).findById(expenseId);
    }

    @Test
    void findAllExpenses_ShouldReturnListOfExpenses() {
        // Arrange
        Expense expense1 = new Expense();
        Expense expense2 = new Expense();
        List<Expense> expenses = Arrays.asList(expense1, expense2);
        when(expensesRepository.findAll()).thenReturn(expenses);

        // Act
        List<Expense> result = expenseService.findAllExpenses();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(expensesRepository, times(1)).findAll();
    }

    @Test
    void findAllExpenses_ShouldReturnEmptyList_WhenNoExpensesExist() {
        // Arrange
        when(expensesRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Expense> result = expenseService.findAllExpenses();

        // Assert
        assertNotNull(result, "findAllExpenses should return an empty list when no expenses exist");
        assertTrue(result.isEmpty());
        verify(expensesRepository, times(1)).findAll();
    }
}
