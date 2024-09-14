package com.microservice.inventory.controller;

import com.microservice.inventory.model.InventoryCreateDTO;
import com.microservice.inventory.model.InventoryResponseDTO;
import com.microservice.inventory.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponseDTO> getAllInventoryItems() {
        return inventoryService.getAllInventoryItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getInventoryItemById(@PathVariable UUID id) {
        InventoryResponseDTO inventory = inventoryService.getInventoryItemById(id);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventoryItem(@RequestBody InventoryCreateDTO createDTO) {
        InventoryResponseDTO createdItem = inventoryService.createInventoryItem(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> updateInventoryItem(@PathVariable UUID id, @RequestBody InventoryCreateDTO createDTO) {
        InventoryResponseDTO updatedItem = inventoryService.updateInventoryItem(id, createDTO);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable UUID id) {
        if (inventoryService.deleteInventoryItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}