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
        Long monthId = 1L;

        Month month = new Month();
        month.setId(monthId);
        month.setYear(2024);
        month.setMonth(1);

        Expense expense = new Expense();
        expense.setPrice(100.0);
        expense.setDescription("Groceries");

        // Mock repository behaviors
        when(monthRepository.findById(monthId)).thenReturn(Optional.of(month));
        when(expensesRepository.save(expense)).thenReturn(expense);

        // Act
        Expense result = expenseService.createExpense(month, expense);

        // Assert
        assertNotNull(result, "The created expense should not be null");
        assertEquals(expense, result, "The returned expense should match the saved expense");

        // Verify that the expense was saved in the repository
        verify(expensesRepository, times(1)).save(expense);

        // Verify that the expense was added to the month
        assertTrue(month.getListOfExpenses().contains(expense), "The month should contain the new expense");
    }


    @Test
    void createExpense_ShouldThrowException_WhenExpenseIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> expenseService.createExpense(null,null));
    }



    @Test
    void deleteExpense_ShouldDeleteExpenseCompletely_WhenExpenseAndMonthExist() {
        // Arrange
        Long expenseId = 1L;

        // Set up the Expense
        Expense expense = new Expense();
        expense.setId(expenseId);

        // Set up the Month and associate the Expense
        Month month = new Month();
        month.setId(1L);
        month.addExpense(expense);

        // Mock repository behaviors
        when(expensesRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(monthRepository.findById(month.getId())).thenReturn(Optional.of(month));
        doNothing().when(expensesRepository).deleteById(expenseId);

        // Act
        boolean result = expenseService.deleteExpense(expenseId);

        // Assert
        assertTrue(result, "deleteExpense should return true for successful deletion");

        // Ensure repository methods are called
        verify(expensesRepository, times(1)).findById(expenseId);
        verify(monthRepository, times(1)).findById(month.getId());
        verify(expensesRepository, times(1)).deleteById(expenseId);

        // Verify that the expense was removed from the month
        assertFalse(month.getListOfExpenses().contains(expense), "Expense should be removed from the month");

        // Verify the expense no longer exists in the repository
        when(expensesRepository.findById(expenseId)).thenReturn(Optional.empty());
        Optional<Expense> deletedExpense = expensesRepository.findById(expenseId);
        assertTrue(deletedExpense.isEmpty(), "Expense should not exist in the repository after deletion");
    }


    @Test
    void deleteExpense_ShouldThrowException_WhenExpenseDoesNotExist() {
        // Arrange
        Long expenseId = 1L;

        when(expensesRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.deleteExpense(expenseId);
        });

        assertEquals("Expense with ID 1 was not found.", exception.getMessage());
        verify(expensesRepository, times(1)).findById(expenseId);
        verify(monthRepository, never()).findById(any());
        verify(expensesRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteExpense_ShouldThrowException_WhenAssociatedMonthDoesNotExist() {
        // Arrange
        Long expenseId = 1L;
        Expense expense = new Expense();
        expense.setId(expenseId);

        Month month = new Month();
        month.setId(1L);
        expense.setMonth(month);

        when(expensesRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(monthRepository.findById(month.getId())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.deleteExpense(expenseId);
        });

        assertEquals("Associated Month was not found.", exception.getMessage());
        verify(expensesRepository, times(1)).findById(expenseId);
        verify(monthRepository, times(1)).findById(month.getId());
        verify(expensesRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteExpense_ShouldThrowException_WhenDeleteFails() {
        // Arrange
        Long expenseId = 1L;
        Expense expense = new Expense();
        expense.setId(expenseId);

        Month month = new Month();
        month.setId(1L);
        expense.setMonth(month);

        when(expensesRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(monthRepository.findById(month.getId())).thenReturn(Optional.of(month));
        doThrow(new RuntimeException("Delete operation failed")).when(expensesRepository).deleteById(expenseId);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            expenseService.deleteExpense(expenseId);
        });

        assertTrue(exception.getMessage().contains("Could not delete expense with ID"));
        verify(expensesRepository, times(1)).findById(expenseId);
        verify(monthRepository, times(1)).findById(month.getId());
        verify(expensesRepository, times(1)).deleteById(expenseId);
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
