package com.microservice.inventory.model;

import java.util.UUID;

public class InventoryResponseDTO {

    private final UUID id;
    private final String productName;
    private final int quantity;
    private final int reservedQuantity;

    public InventoryResponseDTO(UUID id, String productName, int quantity, int reservedQuantity) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public static InventoryResponseDTO fromInventory(Inventory inventory) {
        return new InventoryResponseDTO(inventory.getId(), inventory.getProductName(), inventory.getQuantity(), inventory.getReservedQuantity());
    }
}