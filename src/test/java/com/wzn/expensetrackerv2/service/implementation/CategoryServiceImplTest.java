package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.repository.CategoryRepository;
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

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategory_ShouldSaveCategory() {
        // Arrange
        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(category);

        // Act
        Category result = categoryService.createCategory(category);

        // Assert
        assertNotNull(result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void createCategory_ShouldThrowException_WhenCategoryIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(null));
        assertEquals("Category cannot be null", exception.getMessage());
    }

    @Test
    void deleteCategory_ShouldReturnTrue_WhenCategoryExists() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(categoryId);

        // Act
        boolean result = categoryService.deleteCategory(categoryId);

        // Assert
        assertTrue(result);
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteCategory_ShouldReturnFalse_WhenCategoryDoesNotExist() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Act
        boolean result = categoryService.deleteCategory(categoryId);

        // Assert
        assertFalse(result);
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteCategory_ShouldThrowException_WhenDeletionFails() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        doThrow(new RuntimeException("Deletion error")).when(categoryRepository).deleteById(categoryId);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.deleteCategory(categoryId));
        assertTrue(exception.getMessage().contains("Could not delete Category with ID 1"));
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void findCategoryById_ShouldReturnCategory_WhenCategoryExists() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        Optional<Category> result = categoryService.findCategoryById(categoryId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(categoryId, result.get().getId());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void findCategoryById_ShouldReturnEmptyOptional_WhenCategoryNotFound() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act
        Optional<Category> result = categoryService.findCategoryById(categoryId);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void findAllCategories_ShouldReturnCategories_WhenCategoriesExist() {
        // Arrange
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<Category> result = categoryService.findAllCategories();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }
}
