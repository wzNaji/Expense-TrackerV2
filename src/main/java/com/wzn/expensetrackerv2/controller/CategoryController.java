package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication failed.");
        }

        if (category == null) {
            return ResponseEntity.badRequest().body("Category cannot be null");
        }

        try {
            Category newCategory = categoryService.createCategory(category);
            return ResponseEntity.ok(newCategory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create category: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        return isDeleted ? ResponseEntity.ok().body("Category successfully deleted")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> category = categoryService.findCategoryById(categoryId);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/categoryList")
    public ResponseEntity<?> getCategoryList() {
        List<Category> allCategories = categoryService.findAllCategories();
        return allCategories.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(allCategories);
    }
}
