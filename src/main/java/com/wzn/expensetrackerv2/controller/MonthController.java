package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.service.MonthService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/month")
public class MonthController {

    private final MonthService monthService;

    public MonthController(MonthService monthService) {
        this.monthService = monthService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createMonth(@RequestBody Month month) {
        try {
            Month newMonth = monthService.createMonth(month);
            return ResponseEntity.ok(newMonth);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create month");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMonth(@PathVariable Long id) {
        try {
            boolean isDeleted = monthService.deleteMonth(id);
            if (isDeleted) {
                return ResponseEntity.ok().body("Month successfully deleted");
            } else {
                return ResponseEntity.badRequest().body("Month not found");
            }
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
    @GetMapping("/{monthId}")
    public ResponseEntity<Month> getMonthById(@PathVariable Long monthId) {
        try {
            Month month = monthService.findMonthById(monthId);
            return ResponseEntity.ok(month); // Return 200 OK with the Category
        } catch (IllegalArgumentException ex) {
            // Handle case where the expense is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            // Handle generic exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/monthList")
    public ResponseEntity<List<Month>> getMonthList() {
        try {
            List<Month> allMonths = monthService.findAllMonths();

            if (allMonths.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(allMonths, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // Indicate server error
        }
    }

}
