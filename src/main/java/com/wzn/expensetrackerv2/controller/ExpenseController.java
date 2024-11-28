package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.service.ExpenseService;
import com.wzn.expensetrackerv2.service.MonthService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            // Extract authenticated user's information
            String username = authentication.getName();
            System.out.println("User " + username + " is creating an expense: " + expense);

            // find the month based on the id we get from client
            int year = expense.getMonth().getYear();
            int month = expense.getMonth().getMonth();

            Month associatedMonth = monthService.findByYearAndMonth(year,month);
            System.out.println(associatedMonth.getId());

            if (associatedMonth == null) {
                throw new RuntimeException("A Month is required to create an expense.");
            }

            // Save the Expense
            expenseService.createExpense(associatedMonth, expense);
            return ResponseEntity.ok(expense);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error creating expense: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Failed to create expense: " + e.getMessage());
        }
    }




    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id, Authentication authentication) {
        try {
            // Logging user information from Authentication
            String username = authentication.getName();
            System.out.println("User " + username + " is deleting expense with ID: " + id);

            boolean isDeleted = expenseService.deleteExpense(id);
            if (isDeleted) {
                System.out.println("Expense with ID " + id + " was successfully deleted by user " + username);
                return ResponseEntity.ok().body("Expense successfully deleted");
            } else {
                System.out.println("Expense with ID " + id + " not found for deletion by user " + username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
            }
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete expense: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long expenseId, Authentication authentication) {
        try {
            // Logging user information from Authentication
            String username = authentication.getName();
            System.out.println("User " + username + " is fetching expense with ID: " + expenseId);

            Expense expense = expenseService.findExpenseById(expenseId);
            return ResponseEntity.ok(expense);
        } catch (IllegalArgumentException ex) {
            System.out.println("Expense with ID " + expenseId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error fetching expense: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + ex.getMessage());
        }
    }

    @GetMapping("/expenseList")
    public ResponseEntity<?> getExpenseList(Authentication authentication) {
        try {
            // Logging user information from Authentication
            String username = authentication.getName();
            System.out.println("User " + username + " is fetching the expense list.");

            List<Expense> allExpenses = expenseService.findAllExpenses();

            if (allExpenses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No expenses available");
            } else {
                return ResponseEntity.ok(allExpenses);
            }
        } catch (Exception e) {
            System.out.println("Error fetching expenses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}
