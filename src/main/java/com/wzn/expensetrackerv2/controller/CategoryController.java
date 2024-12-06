package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody Category category, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Unauthorized access");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        try {
            Category result = categoryService.createCategory(category);
            Map<String, Object> response = new HashMap<>();
            if (result.getName() != null) {
                response.put("success", true);
                response.put("message", "Category created");
                return ResponseEntity.ok(response);

            } else {
                response.put("success", false);
                response.put("message", "Category not created");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Internal server error: " + e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Unauthorized access");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        try {
            boolean result = categoryService.deleteCategory(id);
            Map<String, Object> response = new HashMap<>();
            if (result) {
                response.put("success", true);
                response.put("message", "Category deleted successfully.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Category not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/categoryList")
    public ResponseEntity<Map<String, Object>> getCategoryList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Category> allCategories = categoryService.findAllCategories();
            response.put("categories", allCategories);  // Always include the list, empty or not

            if (allCategories.isEmpty()) {
                response.put("success", false);
                response.put("message", "No categories found");
                // Still returns OK status but with an empty list
                return ResponseEntity.ok(response);
            } else {
                response.put("success", true);
                response.put("message", "Categories retrieved successfully");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
