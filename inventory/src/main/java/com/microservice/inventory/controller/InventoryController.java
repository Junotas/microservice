package com.microservice.inventory.controller;

import com.microservice.inventory.model.InventoryCreateDTO;
import com.microservice.inventory.model.InventoryResponseDTO;
import com.microservice.inventory.service.InventoryService;
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
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventoryItems() {
        return ResponseEntity.ok(inventoryService.getAllInventoryItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getInventoryItemById(@PathVariable UUID id) {
        InventoryResponseDTO inventoryItem = inventoryService.getInventoryItemById(id);
        if (inventoryItem != null) {
            return ResponseEntity.ok(inventoryItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventoryItem(@RequestBody InventoryCreateDTO createDTO) {
        return ResponseEntity.ok(inventoryService.createInventoryItem(createDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> updateInventoryItem(@PathVariable UUID id, @RequestBody InventoryCreateDTO createDTO) {
        InventoryResponseDTO updatedItem = inventoryService.updateInventoryItem(id, createDTO);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable UUID id) {
        if (inventoryService.deleteInventoryItem(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}