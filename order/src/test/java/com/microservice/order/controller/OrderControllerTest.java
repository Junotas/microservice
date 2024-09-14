package com.microservice.order.controller;

import com.microservice.order.model.OrderCreateDTO;
import com.microservice.order.model.OrderResponseDTO;
import com.microservice.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testGetAllOrders_emptyList() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testCreateOrder() throws Exception {
        OrderResponseDTO newOrder = new OrderResponseDTO(UUID.randomUUID(), "Test Order", 1, "Pending");
        when(orderService.createOrder(any(OrderCreateDTO.class))).thenReturn(newOrder);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderName\":\"Test Order\", \"quantity\":1, \"status\":\"Pending\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("Test Order"));
    }

    @Test
    void testGetOrderById() throws Exception {
        UUID orderId = UUID.randomUUID();
        OrderResponseDTO order = new OrderResponseDTO(orderId, "Existing Order", 1, "Pending");
        when(orderService.getOrderById(orderId)).thenReturn(order);

        mockMvc.perform(get("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Existing Order"));
    }

}