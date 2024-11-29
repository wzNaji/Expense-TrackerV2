package com.wzn.expensetrackerv2.service.implementation;

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
        monthService.createMonth(month);

        // Assert
        verify(monthRepository, times(1)).save(month);
    }

    @Test
    void createMonth_ShouldThrowException_WhenMonthIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> monthService.createMonth(null));
    }

    @Test
    void deleteMonth_ShouldDeleteMonth() {
        // Arrange
        Long monthId = 1L;
        doNothing().when(monthRepository).deleteById(monthId);

        // Act
        boolean result = monthService.deleteMonth(monthId);

        // Assert
        assertTrue(result);
        verify(monthRepository, times(1)).deleteById(monthId);
    }

    @Test
    void deleteMonth_ShouldThrowException_WhenDeletionFails() {
        // Arrange
        Long monthId = 1L;
        doThrow(new RuntimeException("Deletion error")).when(monthRepository).deleteById(monthId);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> monthService.deleteMonth(monthId));
        assertEquals("Could not delete Month with ID 1", exception.getMessage());
    }

    @Test
    void findMonthById_ShouldReturnMonth() {
        // Arrange
        Long monthId = 1L;
        Month month = new Month();
        month.setId(monthId);
        when(monthRepository.findById(monthId)).thenReturn(Optional.of(month));

        // Act
        Optional<Month> result = monthService.findMonthById(monthId);

        // Assert
        assertNotNull(result);
        assertEquals(monthId, result.get().getId());
        verify(monthRepository, times(1)).findById(monthId);
    }

    @Test
    void findMonthById_ShouldThrowException_WhenMonthNotFound() {
        // Arrange
        Long monthId = 1L;
        when(monthRepository.findById(monthId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> monthService.findMonthById(monthId));
        assertEquals("Month with ID 1 was not found.", exception.getMessage());
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
    void findAllMonths_ShouldThrowException_WhenNoMonthsFound() {
        // Arrange
        when(monthRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> monthService.findAllMonths());
        assertEquals("No months found", exception.getMessage());
    }
}
