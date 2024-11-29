package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.service.MonthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/month")
public class MonthController {

    private final MonthService monthService;

    public MonthController(MonthService monthService) {
        this.monthService = monthService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMonth(@RequestBody Month month) {
        if (month == null) {
            return ResponseEntity.badRequest().body("Month data must not be null");
        }
        try {
            Month newMonth = monthService.createMonth(month);
            return ResponseEntity.ok(newMonth);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create month: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMonth(@PathVariable Long id) {
        boolean isDeleted = monthService.deleteMonth(id);
        if (!isDeleted) {
            return ResponseEntity.badRequest().body("Month not found");
        }
        return ResponseEntity.ok().body("Month successfully deleted");
    }

    @GetMapping("/{monthId}")
    public ResponseEntity<?> getMonthById(@PathVariable Long monthId) {
        Optional<Month> month = monthService.findMonthById(monthId);
        return month.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/monthList")
    public ResponseEntity<List<Month>> getMonthList() {
        List<Month> allMonths = monthService.findAllMonths();
        if (allMonths.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allMonths);
    }

    @GetMapping("/expenseList/{monthId}")
    public ResponseEntity<?> getExpenseList(@PathVariable Long monthId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<Month> month = monthService.findMonthById(monthId);
        if (!month.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<Expense> expenses = monthService.getExpensesByMonth(month.get());
        if (expenses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(expenses);
    }
}
