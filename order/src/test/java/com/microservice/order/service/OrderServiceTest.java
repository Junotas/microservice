package com.microservice.order.service;

import com.microservice.order.model.Order;
import com.microservice.order.model.OrderCreateDTO;
import com.microservice.order.model.OrderResponseDTO;
import com.microservice.order.repository.OrderRepository;
import com.microservice.order.repository.OutboxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository mockOrderRepo;

    @BeforeEach
    void setUp() {
        mockOrderRepo = Mockito.mock(OrderRepository.class);
        OutboxRepository mockOutboxRepo = Mockito.mock(OutboxRepository.class);
        orderService = new OrderService(mockOrderRepo, mockOutboxRepo);  // Ensure both repositories are passed
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Arrange
        OrderCreateDTO newOrderDTO = new OrderCreateDTO("Test Product", 1, "Pending");
        Order savedOrder = new Order(UUID.randomUUID(), "Test Product", 1, "Pending");  // Ensure correct constructor is used

        when(mockOrderRepo.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        OrderResponseDTO createdOrder = orderService.createOrder(newOrderDTO);

        // Assert
        assertNotNull(createdOrder.id(), "Created OrderResponseDTO should have a valid UUID");
        assertEquals("Test Product", createdOrder.productName());
        verify(mockOrderRepo, times(1)).save(any(Order.class));
    }

    @Test
    void shouldReturnOrder_whenOrderExists() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Order existingOrder = new Order(orderId, "Existing Product", 1, "Pending");
        when(mockOrderRepo.findById(orderId)).thenReturn(Optional.of(existingOrder));

        // Act
        OrderResponseDTO foundOrder = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(foundOrder);
        assertEquals(orderId, foundOrder.id());
        assertEquals("Existing Product", foundOrder.productName());
        verify(mockOrderRepo, times(1)).findById(orderId);
    }

    @Test
    void shouldReturnNull_whenOrderDoesNotExist() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        when(mockOrderRepo.findById(orderId)).thenReturn(Optional.empty());

        // Act
        OrderResponseDTO foundOrder = orderService.getOrderById(orderId);

        // Assert
        assertNull(foundOrder, "Order should return null when not found");
        verify(mockOrderRepo, times(1)).findById(orderId);
    }

    @Test
    void shouldDeleteOrder_whenOrderExists() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        when(mockOrderRepo.existsById(orderId)).thenReturn(true);

        // Act
        boolean deleted = orderService.deleteOrder(orderId);

        // Assert
        assertTrue(deleted);
        verify(mockOrderRepo, times(1)).deleteById(orderId);
    }
}