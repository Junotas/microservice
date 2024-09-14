package com.microservice.order.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @UuidGenerator
    private UUID id;
    private String productName;
    private int quantity;
    private String status;

    public Order() {}

    public Order(String productName, int quantity, String status) {
        this.productName = productName;
        this.quantity = quantity;
        this.status = status;
    }

    public Order(UUID id, String productName, int quantity, String status) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}