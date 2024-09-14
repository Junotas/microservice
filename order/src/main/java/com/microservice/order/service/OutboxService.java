package com.microservice.order.service;

import com.microservice.order.model.OutboxEvent;
import com.microservice.order.repository.OutboxRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OutboxService {

    private final OutboxRepository outboxRepository;

    public OutboxService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    public List<OutboxEvent> getAllEvents() {
        return outboxRepository.findAll();
    }

    public OutboxEvent getEventById(UUID id) {
        return outboxRepository.findById(id).orElse(null);
    }

    public OutboxEvent createEvent(OutboxEvent event) {
        return outboxRepository.save(event);
    }

    public void deleteEvent(UUID id) {
        outboxRepository.deleteById(id);
    }
}