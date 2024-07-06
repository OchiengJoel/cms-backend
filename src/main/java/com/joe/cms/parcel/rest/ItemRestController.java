package com.joe.cms.parcel.rest;

import com.joe.cms.parcel.dto.ItemDTO;
import com.joe.cms.parcel.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
//@CrossOrigin("http://localhost:4200")
public class ItemRestController {

    private final ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAllItems() {
        try {
            List<ItemDTO> items = itemService.listAllItems();
            if (items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items found");
            } else {
                return ResponseEntity.ok(items);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving items: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findItemById(@PathVariable Long id) {
        try {
            ItemDTO item = itemService.findItemById(id);
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Item not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving item: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createItem(@RequestBody ItemDTO itemDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            ItemDTO createdItem = itemService.createItem(itemDTO);
            response.put("message", "Item Created Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
           // return ResponseEntity.status(201).body(createdItem);
        } catch (IllegalArgumentException e) {
            response.put("message", "Invalid input: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            //return ResponseEntity.status(400).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            response.put("message", "Error creating item: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            //return ResponseEntity.status(500).body("Error creating item: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {

        Map<String, String> response = new HashMap<>();
        try {
            itemDTO.setId(id);
            ItemDTO updatedItem = itemService.updateItem(itemDTO);
            response.put("message", "Item Updated Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
            //return ResponseEntity.ok(updatedItem);
        } catch (IllegalArgumentException e) {
            response.put("message", "Item not found: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
           // return ResponseEntity.status(404).body("Item not found: " + e.getMessage());
        } catch (Exception e) {
            response.put("message", "Error updating item: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            //return ResponseEntity.status(500).body("Error updating item: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Item not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting item: " + e.getMessage());
        }
    }
}
