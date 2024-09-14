package com.microservice.order.repository;

import com.microservice.order.model.OutboxEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OutboxRepositoryTest {

    @Autowired
    private OutboxRepository outboxRepository;

    @Test
    @Transactional
    @Rollback(false)  // Allow the transaction to be committed to ensure persistence
    void shouldCreateAndFindOutboxEvent() {
        // Arrange
        UUID eventId = UUID.randomUUID();
        OutboxEvent outboxEvent = new OutboxEvent(eventId, "Test Event", "Pending");
        outboxRepository.save(outboxEvent);

        // Act
        OutboxEvent foundEvent = outboxRepository.findById(eventId).orElse(null);

        // Assert
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getEventType()).isEqualTo("Test Event");
    }

    @Test
    @Transactional
    @Rollback
    void shouldDeleteOutboxEvent() {
        // Arrange
        UUID eventId = UUID.randomUUID();
        OutboxEvent outboxEvent = new OutboxEvent(eventId, "Test Event", "Pending");
        outboxRepository.save(outboxEvent);

        // Act
        outboxRepository.deleteById(eventId);

        // Assert
        assertThat(outboxRepository.findById(eventId)).isEmpty();
    }
}