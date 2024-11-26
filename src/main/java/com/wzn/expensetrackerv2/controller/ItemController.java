package com.wzn.expensetrackerv2.controller;
import com.wzn.expensetrackerv2.entity.Item;
import com.wzn.expensetrackerv2.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        try {
            Item newItem = itemService.createItem(item);
            return ResponseEntity.ok(newItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create item");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            boolean isDeleted = itemService.deleteItem(id);
            if (isDeleted) {
                return ResponseEntity.ok().body("Item successfully deleted");
            } else {
                return ResponseEntity.badRequest().body("Item not found");
            }
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable Long itemId) {
        try {
            Item item = itemService.findItemById(itemId);
            return ResponseEntity.ok(item); // Return 200 OK with the Category
        } catch (IllegalArgumentException ex) {
            // Handle case where the expense is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            // Handle generic exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/itemList")
    public ResponseEntity<List<Item>> getItemList() {
        try {
            List<Item> allItems = itemService.findAllItems();

            if (allItems.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(allItems, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // Indicate server error
        }
    }

}



