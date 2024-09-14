package com.microservice.inventory.model;

public record InventoryCreateDTO(String productName, int quantity, int reservedQuantity) {

    public Inventory toInventory() {
        return new Inventory(productName, quantity, reservedQuantity);
    }
}