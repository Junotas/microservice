package com.microservice.inventory.controller;

import com.microservice.inventory.model.InventoryCreateDTO;
import com.microservice.inventory.model.InventoryResponseDTO;
import com.microservice.inventory.service.InventoryService;
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

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void testGetAllInventoryItems_emptyList() throws Exception {
        when(inventoryService.getAllInventoryItems()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testCreateInventoryItem() throws Exception {
        InventoryResponseDTO newItem = new InventoryResponseDTO(UUID.randomUUID(), "New Product", 10, 1);
        when(inventoryService.createInventoryItem(any(InventoryCreateDTO.class))).thenReturn(newItem);
        mockMvc.perform(post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productName\":\"New Product\",\"quantity\":10,\"reservedQuantity\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("New Product"));
    }

    @Test
    void testGetInventoryItemById_found() throws Exception {
        UUID id = UUID.randomUUID();
        InventoryResponseDTO item = new InventoryResponseDTO(id, "Existing Product", 10, 1);
        when(inventoryService.getInventoryItemById(id)).thenReturn(item);
        mockMvc.perform(get("/api/inventory/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Existing Product"));
    }

    @Test
    void testGetInventoryItemById_notFound() throws Exception {
        when(inventoryService.getInventoryItemById(any(UUID.class))).thenReturn(null);
        mockMvc.perform(get("/api/inventory/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteInventoryItem() throws Exception {
        UUID id = UUID.randomUUID();
        when(inventoryService.deleteInventoryItem(any(UUID.class))).thenReturn(true);
        mockMvc.perform(delete("/api/inventory/{id}", id))
                .andExpect(status().isNoContent());
    }
}