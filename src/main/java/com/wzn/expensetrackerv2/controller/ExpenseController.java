package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@RequestBody Month month,
                                           Expense expense) {
        try {
            Expense newExpense = expenseService.createExpense(month,expense);
            return ResponseEntity.ok(newExpense);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create expense");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        try {
            boolean isDeleted = expenseService.deleteExpense(id);
            if (isDeleted) {
                return ResponseEntity.ok().body("Expense successfully deleted");
            } else {
                return ResponseEntity.badRequest().body("Expense not found");
            }
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
    @GetMapping("/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long expenseId) {
        try {
            Expense expense = expenseService.findExpenseById(expenseId);
            return ResponseEntity.ok(expense); // Return 200 OK with the Category
        } catch (IllegalArgumentException ex) {
            // Handle case where the expense is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            // Handle generic exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/expenseList")
    public ResponseEntity<List<Expense>> getExpenseList() {
        try {
            List<Expense> allExpenses = expenseService.findAllExpenses();

            if (allExpenses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(allExpenses, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // Indicate server error
        }
    }

}
