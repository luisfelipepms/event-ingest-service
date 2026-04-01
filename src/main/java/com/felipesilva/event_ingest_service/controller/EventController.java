package com.felipesilva.event_ingest_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felipesilva.event_ingest_service.domain.dto.EventDTO;
import com.felipesilva.event_ingest_service.domain.entity.Event;
import com.felipesilva.event_ingest_service.service.EventService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;

    public EventController(EventService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody @Valid EventDTO dto){
        Event event = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    // @GetMapping()
    // public ResponseEntity<List<Event>> listEvents() {
    //     List<Event> event = service.listAll();
    //     return ResponseEntity.status(HttpStatus.OK).body(event);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID id){
        Event event = service.getEventById(id);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @GetMapping()
    public ResponseEntity<List<Event>> findByType(@RequestParam(required = false) String type){
        List<Event> event = service.findByType(type);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }
}
