package com.microservice.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Updated to AUTO to ensure UUID is generated
    private UUID id;
    private String productName;
    private int quantity;
    private int reservedQuantity;

    public Inventory() {
    }

    public Inventory(String productName, int quantity, int reservedQuantity) {
        this.productName = productName;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
    }

    public Inventory(UUID id, String productName, int quantity, int reservedQuantity) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }
}