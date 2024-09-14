package com.microservice.inventory.model;

import java.util.UUID;

public record InventoryResponseDTO(UUID id, String productName, int quantity, int reservedQuantity) {

    public static InventoryResponseDTO fromInventory(Inventory inventory) {
        return new InventoryResponseDTO(inventory.getId(), inventory.getProductName(), inventory.getQuantity(), inventory.getReservedQuantity());
    }
}