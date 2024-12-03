package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.service.ExpenseService;
import com.wzn.expensetrackerv2.service.MonthService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final MonthService monthService;

    public ExpenseController(ExpenseService expenseService, MonthService monthService) {
        this.expenseService = expenseService;
        this.monthService = monthService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@RequestBody Expense expense, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Access Denied: User is not authenticated.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Handle unauthenticated requests
        }

        if (expense == null || expense.getMonth() == null) {
            System.out.println("Error: Expense and Month details are required.");
            return ResponseEntity.badRequest().body("Expense and Month details are required."); // Basic input validation
        }

        String username = authentication.getName();
        System.out.println("User " + username + " is creating an expense: " + expense);

        Optional<Month> associatedMonth = monthService.findMonthById(expense.getMonth().getId());
        //Month associatedMonth = monthService.findByYearAndMonth(expense.getMonth().getYear(), expense.getMonth().getMonth());
        if (associatedMonth.isEmpty()) {
            System.out.println("Error: Month not found for year " + expense.getMonth().getYear() + " and month " + expense.getMonth().getMonth());
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
        System.out.println("User " + username + "is attempting to delete expense with ID: " + id);

        try {
            if (expenseService.deleteExpense(id)) {
                System.out.println("Expense with ID " + id + "was successfully deleted by user " + username);
                return ResponseEntity.ok().body("Expense successfully deleted");
            } else {
                System.out.println("Attempt to delete non-existing expense with ID " + id + "by user " + username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while deleting expense with ID " + id + ": " +  e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error: " + e.getMessage());
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
