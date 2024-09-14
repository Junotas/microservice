package com.microservice.inventory.service;

import com.microservice.inventory.model.Inventory;
import com.microservice.inventory.model.InventoryCreateDTO;
import com.microservice.inventory.model.InventoryResponseDTO;
import com.microservice.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    private InventoryService inventoryService;
    private InventoryRepository mockRepo;

    @BeforeEach
    void setUp() {
        mockRepo = Mockito.mock(InventoryRepository.class);
        inventoryService = new InventoryService(mockRepo);
    }

    @Test
    void shouldReturnEmptyList_whenNoInventoryItemsExist() {
        // Arrange
        when(mockRepo.findAll()).thenReturn(List.of());

        // Act
        List<InventoryResponseDTO> result = inventoryService.getAllInventoryItems();

        // Assert
        assertTrue(result.isEmpty(), "InventoryResponseDTO list should be empty");
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void shouldCreateInventoryItemSuccessfully() {
        // Arrange
        Inventory newItem = new Inventory(UUID.randomUUID(), "Test Product", 10, 2);
        when(mockRepo.save(any(Inventory.class))).thenAnswer(invocation -> {
            Inventory inventory = invocation.getArgument(0);
            inventory.setId(UUID.randomUUID()); // Simulate UUID generation
            return inventory;
        });

        // Act
        InventoryResponseDTO createdItem = inventoryService.createInventoryItem(new InventoryCreateDTO("Test Product", 10, 2));

        // Assert
        assertNotNull(createdItem.id(), "Created InventoryResponseDTO should have a valid UUID");
        verify(mockRepo, times(1)).save(any(Inventory.class));
    }


    @Test
    void shouldReturnInventoryItem_whenItemExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        Inventory existingItem = new Inventory(id, "Existing Product", 10, 1);
        when(mockRepo.findById(id)).thenReturn(Optional.of(existingItem));

        // Act
        InventoryResponseDTO foundItem = inventoryService.getInventoryItemById(id);

        // Assert
        assertNotNull(foundItem, "InventoryResponseDTO should be returned when the item exists");
        assertEquals(id, foundItem.id());
        assertEquals("Existing Product", foundItem.productName());
        verify(mockRepo, times(1)).findById(id);
    }

    @Test
    void shouldReturnNull_whenItemDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(mockRepo.findById(id)).thenReturn(Optional.empty());

        // Act
        InventoryResponseDTO foundItem = inventoryService.getInventoryItemById(id);

        // Assert
        assertNull(foundItem, "InventoryResponseDTO should be null when the item is not found");
        verify(mockRepo, times(1)).findById(id);
    }

    @Test
    void shouldUpdateInventoryItemSuccessfully() {
        // Arrange
        UUID id = UUID.randomUUID();
        Inventory existingItem = new Inventory(id, "Old Product", 5, 0);
        InventoryCreateDTO updateDTO = new InventoryCreateDTO("Updated Product", 15, 5);
        when(mockRepo.findById(id)).thenReturn(Optional.of(existingItem));
        when(mockRepo.save(any(Inventory.class))).thenReturn(existingItem);

        // Act
        InventoryResponseDTO updatedItem = inventoryService.updateInventoryItem(id, updateDTO);

        // Assert
        assertNotNull(updatedItem, "Updated InventoryResponseDTO should not be null");
        assertEquals("Updated Product", updatedItem.productName());
        assertEquals(15, updatedItem.quantity());
        assertEquals(5, updatedItem.reservedQuantity());
        verify(mockRepo, times(1)).findById(id);
        verify(mockRepo, times(1)).save(existingItem);
    }

    @Test
    void shouldReturnNull_whenUpdatingNonExistentItem() {
        // Arrange
        UUID id = UUID.randomUUID();
        InventoryCreateDTO updateDTO = new InventoryCreateDTO("Updated Product", 15, 5);
        when(mockRepo.findById(id)).thenReturn(Optional.empty());

        // Act
        InventoryResponseDTO updatedItem = inventoryService.updateInventoryItem(id, updateDTO);

        // Assert
        assertNull(updatedItem, "Update should return null if the item does not exist");
        verify(mockRepo, times(1)).findById(id);
        verify(mockRepo, times(0)).save(any(Inventory.class));
    }

    @Test
    void shouldDeleteInventoryItem_whenItemExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(mockRepo.existsById(id)).thenReturn(true);

        // Act
        boolean deleted = inventoryService.deleteInventoryItem(id);

        // Assert
        assertTrue(deleted, "Inventory item should be deleted successfully");
        verify(mockRepo, times(1)).deleteById(id);
    }

    @Test
    void shouldNotDeleteInventoryItem_whenItemDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(mockRepo.existsById(id)).thenReturn(false);

        // Act
        boolean deleted = inventoryService.deleteInventoryItem(id);

        // Assert
        assertFalse(deleted, "Inventory item should not be deleted when it does not exist");
        verify(mockRepo, times(1)).existsById(id);
    }
}