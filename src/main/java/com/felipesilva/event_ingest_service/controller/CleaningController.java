package com.felipesilva.event_ingest_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipesilva.event_ingest_service.domain.dto.CleaningDTO;
import com.felipesilva.event_ingest_service.domain.entity.Cleaning;
import com.felipesilva.event_ingest_service.service.CleaningService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/cleanings")
public class CleaningController {
    private final CleaningService service;

    public CleaningController(CleaningService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Cleaning> save(@RequestBody @Valid CleaningDTO dto) {
        Cleaning cleaning = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cleaning);
    }
    
}
