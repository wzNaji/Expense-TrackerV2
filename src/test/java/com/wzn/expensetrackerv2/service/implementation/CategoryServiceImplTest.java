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
        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(category);

        categoryService.createCategory(category);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void createCategory_ShouldThrowException_WhenCategoryIsNull() {
        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(null));
    }

    @Test
    void deleteCategory_ShouldDeleteCategory() {
        // Arrange
        Long categoryId = 1L;
        doNothing().when(categoryRepository).deleteById(categoryId);

        // Act
        boolean result = categoryService.deleteCategory(categoryId);

        // Assert
        assertTrue(result);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteCategory_ShouldThrowException_WhenDeletionFails() {
        // Arrange
        Long categoryId = 1L;
        doThrow(new RuntimeException("Deletion error")).when(categoryRepository).deleteById(categoryId);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.deleteCategory(categoryId));
        assertEquals("Could not delete Category with ID 1", exception.getMessage());
    }

    @Test
    void findCategoryById_ShouldReturnCategory() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        Category result = categoryService.findCategoryById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void findCategoryById_ShouldThrowException_WhenCategoryNotFound() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.findCategoryById(categoryId));
        assertEquals("Expense with ID 1 was not found.", exception.getMessage());
    }

    @Test
    void findAllCategories_ShouldReturnCategories() {
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

    @Test
    void findAllCategories_ShouldThrowException_WhenNoCategoriesFound() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.findAllCategories());
        assertEquals("No categories found", exception.getMessage());
    }
}
