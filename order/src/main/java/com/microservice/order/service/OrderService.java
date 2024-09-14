package com.microservice.order.service;

import com.microservice.order.model.Order;
import com.microservice.order.model.OrderCreateDTO;
import com.microservice.order.model.OrderResponseDTO;
import com.microservice.order.model.OutboxEvent;
import com.microservice.order.repository.OrderRepository;
import com.microservice.order.repository.OutboxRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponseDTO::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(UUID id) {
        return orderRepository.findById(id)
                .map(OrderResponseDTO::fromOrder)
                .orElse(null);
    }

    public OrderResponseDTO createOrder(OrderCreateDTO orderCreateDTO) {
        Order savedOrder = orderRepository.save(orderCreateDTO.toOrder());
        // Updated constructor to use the correct three-argument constructor
        OutboxEvent event = new OutboxEvent(UUID.randomUUID(), "OrderCreated", savedOrder.getId().toString());
        outboxRepository.save(event);
        return OrderResponseDTO.fromOrder(savedOrder);
    }

    public OrderResponseDTO updateOrder(UUID id, OrderCreateDTO orderCreateDTO) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setProductName(orderCreateDTO.productName());
                    existingOrder.setQuantity(orderCreateDTO.quantity());
                    existingOrder.setStatus(orderCreateDTO.status());
                    return orderRepository.save(existingOrder);
                })
                .map(OrderResponseDTO::fromOrder)
                .orElse(null);
    }

    public boolean deleteOrder(UUID id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}