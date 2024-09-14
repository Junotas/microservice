package com.microservice.inventory.service;

import com.microservice.inventory.model.Inventory;
import com.microservice.inventory.model.InventoryCreateDTO;
import com.microservice.inventory.repository.InventoryRepository;
import com.microservice.inventory.model.InventoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryResponseDTO> getAllInventoryItems() {
        return inventoryRepository.findAll()
                .stream()
                .map(InventoryResponseDTO::fromInventory)
                .collect(Collectors.toList());
    }

    public InventoryResponseDTO getInventoryItemById(UUID id) {
        return inventoryRepository.findById(id)
                .map(InventoryResponseDTO::fromInventory)
                .orElse(null);
    }

    public InventoryResponseDTO createInventoryItem(InventoryCreateDTO createDTO) {
        Inventory savedInventory = inventoryRepository.save(createDTO.toInventory());
        return InventoryResponseDTO.fromInventory(savedInventory);
    }

    public InventoryResponseDTO updateInventoryItem(UUID id, InventoryCreateDTO createDTO) {
        return inventoryRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setProductName(createDTO.productName());
                    existingItem.setQuantity(createDTO.quantity());
                    existingItem.setReservedQuantity(createDTO.reservedQuantity());
                    return inventoryRepository.save(existingItem);
                })
                .map(InventoryResponseDTO::fromInventory)
                .orElse(null);
    }

    public boolean deleteInventoryItem(UUID id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}