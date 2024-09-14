package com.microservice.order.model;

import java.util.UUID;

public record OrderResponseDTO(UUID id, String productName, int quantity, String status) {

    public static OrderResponseDTO fromOrder(Order order) {
        return new OrderResponseDTO(order.getId(), order.getProductName(), order.getQuantity(), order.getStatus());
    }
}