package com.microservice.order.model;

public record OrderCreateDTO(String productName, int quantity, String status) {

    public Order toOrder() {
        return new Order(productName, quantity, status);
    }
}