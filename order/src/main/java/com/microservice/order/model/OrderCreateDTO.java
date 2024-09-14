package com.microservice.order.model;

import java.util.UUID;

public record OrderCreateDTO(String productName, int quantity, String status) {

    public Order toOrder() {
        // UUID is generated here
        return new Order(UUID.randomUUID(), productName, quantity, status);
    }
}