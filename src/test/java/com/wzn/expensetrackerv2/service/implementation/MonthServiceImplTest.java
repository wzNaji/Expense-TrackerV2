package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
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

class MonthServiceImplTest {

    @Mock
    private MonthRepository monthRepository;

    @InjectMocks
    private MonthServiceImpl monthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMonth_ShouldSaveMonth() {
        // Arrange
        Month month = new Month();
        when(monthRepository.save(month)).thenReturn(month);

        // Act
        Month savedMonth = monthService.createMonth(month);

        // Assert
        verify(monthRepository, times(1)).save(month);
        assertNotNull(savedMonth);
    }

    @Test
    void createMonth_ShouldThrowException_WhenMonthIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> monthService.createMonth(null));
        assertEquals("Month data must not be null", exception.getMessage());
    }

    @Test
    void deleteMonth_ShouldDeleteMonth_WhenExists() {
        // Arrange
        Long monthId = 1L;
        when(monthRepository.existsById(monthId)).thenReturn(true);
        doNothing().when(monthRepository).deleteById(monthId);

        // Act
        boolean result = monthService.deleteMonth(monthId);

        // Assert
        assertTrue(result);
        verify(monthRepository, times(1)).deleteById(monthId);
    }

    @Test
    void deleteMonth_ShouldReturnFalse_WhenMonthDoesNotExist() {
        // Arrange
        Long monthId = 1L;
        when(monthRepository.existsById(monthId)).thenReturn(false);

        // Act
        boolean result = monthService.deleteMonth(monthId);

        // Assert
        assertFalse(result);
        verify(monthRepository, never()).deleteById(anyLong());
    }

    @Test
    void findMonthById_ShouldReturnMonth_WhenFound() {
        // Arrange
        Long monthId = 1L;
        Month month = new Month();
        month.setId(monthId);
        when(monthRepository.findById(monthId)).thenReturn(Optional.of(month));

        // Act
        Optional<Month> result = monthService.findMonthById(monthId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(monthId, result.get().getId());
        verify(monthRepository, times(1)).findById(monthId);
    }

    @Test
    void findMonthById_ShouldReturnEmptyOptional_WhenNotFound() {
        // Arrange
        Long monthId = 1L;
        when(monthRepository.findById(monthId)).thenReturn(Optional.empty());

        // Act
        Optional<Month> result = monthService.findMonthById(monthId);

        // Assert
        assertFalse(result.isPresent());
        verify(monthRepository, times(1)).findById(monthId);
    }

    @Test
    void findAllMonths_ShouldReturnMonths() {
        // Arrange
        Month month1 = new Month();
        Month month2 = new Month();
        List<Month> months = Arrays.asList(month1, month2);
        when(monthRepository.findAll()).thenReturn(months);

        // Act
        List<Month> result = monthService.findAllMonths();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(monthRepository, times(1)).findAll();
    }

    @Test
    void findByYearAndMonth_ShouldReturnMonth() {
        // Arrange
        int year = 2023;
        int monthNumber = 11;
        Month month = new Month();
        when(monthRepository.findByYearAndMonth(year, monthNumber)).thenReturn(month);

        // Act
        Month result = monthService.findByYearAndMonth(year, monthNumber);

        // Assert
        assertNotNull(result);
        verify(monthRepository, times(1)).findByYearAndMonth(year, monthNumber);
    }

    @Test
    void getExpensesByMonth_ShouldReturnExpenses() {
        // Arrange
        Month month = new Month();
        Expense expense1 = new Expense();
        Expense expense2 = new Expense();
        month.setListOfExpenses(Arrays.asList(expense1, expense2));

        // Act
        List<Expense> expenses = monthService.getExpensesByMonth(month);

        // Assert
        assertNotNull(expenses);
        assertEquals(2, expenses.size());
    }

    @Test
    void getExpensesByMonth_ShouldThrowException_WhenMonthIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> monthService.getExpensesByMonth(null));
        assertEquals("Month cannot be null", exception.getMessage());
    }
}
