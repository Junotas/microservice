package com.microservice.order.repository;

import com.microservice.order.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    @Rollback(false)  // Allow the transaction to be committed to ensure persistence
    void shouldCreateAndFindOrder() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, "Test Product", 1, "Pending");
        orderRepository.save(order);

        // Act
        Order foundOrder = orderRepository.findById(orderId).orElse(null);

        // Assert
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getProductName()).isEqualTo("Test Product");
    }

    @Test
    @Transactional
    @Rollback
    void shouldDeleteOrder() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, "Test Product", 1, "Pending");
        orderRepository.save(order);

        // Act
        orderRepository.deleteById(orderId);

        // Assert
        assertThat(orderRepository.findById(orderId)).isEmpty();
    }
}