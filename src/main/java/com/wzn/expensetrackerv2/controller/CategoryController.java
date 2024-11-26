package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category newCategory = categoryService.createCategory(category);
            return ResponseEntity.ok(newCategory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create category");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            boolean isDeleted = categoryService.deleteCategory(id);
            if (isDeleted) {
                return ResponseEntity.ok().body("Category successfully deleted");
            } else {
                return ResponseEntity.badRequest().body("Category not found");
            }
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            return ResponseEntity.ok(category); // Return 200 OK with the Category
        } catch (IllegalArgumentException ex) {
            // Handle case where the Category is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            // Handle generic exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/categoryList")
    public ResponseEntity<List<Category>> getCategoryList() {
        try {
            List<Category> allCategories = categoryService.findAllCategories();

            if (allCategories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(allCategories, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // Indicate server error
        }
    }

}
