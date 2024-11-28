package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category, Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                System.out.println("Unauthenticated request.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication failed.");
            }

            String username = authentication.getName();
            System.out.println("User " + username + " is creating a category.");

            Category newCategory = categoryService.createCategory(category);
            return ResponseEntity.ok(newCategory);
        } catch (Exception e) {
            System.out.println("Error creating category: " + e.getMessage());
            return ResponseEntity.badRequest().body("Failed to create category: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id, Authentication authentication) {
        try {
            // Logging user information from Authentication
            String username = authentication.getName();
            System.out.println("User " + username + " is deleting category with ID: " + id);

            boolean isDeleted = categoryService.deleteCategory(id);
            if (isDeleted) {
                return ResponseEntity.ok().body("Category successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId, Authentication authentication) {
        try {
            // Logging user information from Authentication
            String username = authentication.getName();
            System.out.println("User " + username + " is fetching category with ID: " + categoryId);

            Category category = categoryService.findCategoryById(categoryId);
            return ResponseEntity.ok(category);
        } catch (IllegalArgumentException ex) {
            // Handle case where the category is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found: " + ex.getMessage());
        } catch (Exception ex) {
            // Handle generic exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + ex.getMessage());
        }
    }

    @GetMapping("/categoryList")
    public ResponseEntity<?> getCategoryList(Authentication authentication) {
        try {
            // Logging user information from Authentication
            String username = authentication.getName();
            System.out.println("User " + username + " is fetching the category list.");

            List<Category> allCategories = categoryService.findAllCategories();
            if (allCategories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No categories available");
            } else {
                return ResponseEntity.ok(allCategories);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}
