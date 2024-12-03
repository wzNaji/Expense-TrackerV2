package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.service.CategoryService;
import com.wzn.expensetrackerv2.service.ExpenseService;
import com.wzn.expensetrackerv2.service.MonthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final MonthService monthService;
    public ExpenseController(ExpenseService expenseService, MonthService monthService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.monthService = monthService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@RequestBody Expense expense, Authentication authentication) {
        // auth
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Access Denied: User is not authenticated.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Handle unauthenticated requests
        }

        // null checks
        if (expense == null || expense.getMonth() == null) {
            System.out.println("Error: Expense and Month details are required.");
            return ResponseEntity.badRequest().body("Expense and Month details are required."); // Basic input validation
        }

        // Logging
        String username = authentication.getName();
        System.out.println("User " + username + " is creating an expense: " + expense);

        // find month associated with the expense so we can add the month object to the expense in the service
        Optional<Month> associatedMonth = monthService.findMonthById(expense.getMonth().getId());
        if (associatedMonth.isEmpty()) {
            System.out.println("Error: Month not found for id: " + expense.getMonth().getId());
            return ResponseEntity.badRequest().body("The specified month does not exist.");
        }

        try {
            Expense createdExpense = expenseService.createExpense(associatedMonth.get(), expense);
            return ResponseEntity.ok(createdExpense);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Data integrity violation while saving expense: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data integrity issue when saving the expense.");
        } catch (Exception e) {
            System.out.println("Unexpected error occurred while saving expense: " + e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred while creating the expense.");
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Handle unauthenticated requests
        }

        String username = authentication.getName();
        System.out.println("User " + username + " is attempting to delete expense with ID: " + id);

        try {
            boolean result = expenseService.deleteExpense(id);
            if (result) {
                System.out.println("Expense with ID " + id + " was successfully deleted by user " + username);
                // Respond with a JSON object containing the success message
                return ResponseEntity.ok().build();
            } else {
                System.out.println("Attempt to delete non-existing expense with ID " + id + " by user " + username);
                // Respond with a JSON object indicating the expense was not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            System.out.println("Error occurred while deleting expense with ID " + id + ": " + e.getMessage());
            // Respond with a JSON object containing the error message
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long expenseId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // If no authentication details are available
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        String username = authentication.getName();
        System.out.println("User " + username + " is fetching expense with ID: " + expenseId);

        Optional<Expense> expenseOpt = expenseService.findExpenseById(expenseId);
        if (expenseOpt.isEmpty()) {
            System.out.println("Expense with ID " + expenseId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }
        return ResponseEntity.ok(expenseOpt.get());
    }

}
