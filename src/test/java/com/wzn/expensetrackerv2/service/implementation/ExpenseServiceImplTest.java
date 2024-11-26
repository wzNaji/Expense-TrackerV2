package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.repository.ExpensesRepository;
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
    private ExpensesRepository expensesRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createExpense_ShouldSaveExpense() {
        // Arrange
        Expense expense = new Expense();
        when(expensesRepository.save(expense)).thenReturn(expense);

        // Act
        expenseService.createExpense(expense);

        // Assert
        verify(expensesRepository, times(1)).save(expense);
    }

    @Test
    void createExpense_ShouldThrowException_WhenExpenseIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> expenseService.createExpense(null));
    }

    @Test
    void deleteExpense_ShouldDeleteExpense() {
        // Arrange
        Long expenseId = 1L;
        doNothing().when(expensesRepository).deleteById(expenseId);

        // Act
        boolean result = expenseService.deleteExpense(expenseId);

        // Assert
        assertTrue(result);
        verify(expensesRepository, times(1)).deleteById(expenseId);
    }

    @Test
    void deleteExpense_ShouldThrowException_WhenDeletionFails() {
        // Arrange
        Long expenseId = 1L;
        doThrow(new RuntimeException("Deletion error")).when(expensesRepository).deleteById(expenseId);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> expenseService.deleteExpense(expenseId));
        assertEquals("Could not delete expense with ID 1", exception.getMessage());
    }

    @Test
    void findExpenseById_ShouldReturnExpense() {
        // Arrange
        Long expenseId = 1L;
        Expense expense = new Expense();
        expense.setId(expenseId);
        when(expensesRepository.findById(expenseId)).thenReturn(Optional.of(expense));

        // Act
        Expense result = expenseService.findExpenseById(expenseId);

        // Assert
        assertNotNull(result);
        assertEquals(expenseId, result.getId());
        verify(expensesRepository, times(1)).findById(expenseId);
    }

    @Test
    void findExpenseById_ShouldThrowException_WhenExpenseNotFound() {
        // Arrange
        Long expenseId = 1L;
        when(expensesRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> expenseService.findExpenseById(expenseId));
        assertEquals("Expense with ID 1 was not found.", exception.getMessage());
    }

    @Test
    void findAllExpenses_ShouldReturnExpenses() {
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
    void findAllExpenses_ShouldThrowException_WhenNoExpensesFound() {
        // Arrange
        when(expensesRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> expenseService.findAllExpenses());
        assertEquals("No expenses found", exception.getMessage());
    }
}
