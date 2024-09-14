package com.microservice.inventory.repository;

import com.microservice.inventory.model.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    @Transactional
    @Rollback
    void shouldCreateAndFindInventoryItem() {
        Inventory item = new Inventory("Test Product", 10, 2);
        inventoryRepository.save(item);
        Inventory foundItem = inventoryRepository.findById(item.getId()).orElse(null);
        assertThat(foundItem).isNotNull();
        assertThat(foundItem.getProductName()).isEqualTo("Test Product");
    }

    @Test
    @Transactional
    @Rollback
    void shouldDeleteInventoryItem() {
        Inventory item = new Inventory("Test Product", 10, 2);
        inventoryRepository.save(item);
        inventoryRepository.deleteById(item.getId());
        assertThat(inventoryRepository.findById(item.getId())).isEmpty();
    }
}