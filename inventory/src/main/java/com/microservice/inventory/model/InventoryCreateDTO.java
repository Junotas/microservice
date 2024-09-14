package com.microservice.inventory.model;

public class InventoryCreateDTO {

    private final String productName;
    private final int quantity;
    private final int reservedQuantity;

    public InventoryCreateDTO(String productName, int quantity, int reservedQuantity) {
        this.productName = productName;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
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

    public Inventory toInventory() {
        return new Inventory(productName, quantity, reservedQuantity);
    }
}