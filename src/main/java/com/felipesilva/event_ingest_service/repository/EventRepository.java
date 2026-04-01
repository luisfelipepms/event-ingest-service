package com.felipesilva.event_ingest_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesilva.event_ingest_service.domain.entity.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {

    public List<Event> findByType(String type);
    
}