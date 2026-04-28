package com.felipesilva.event_ingest_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningEndDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningResponseDTO;
import com.felipesilva.event_ingest_service.service.CleaningService;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<CleaningResponseDTO> save(@RequestBody @Valid CleaningDTO dto) {
        CleaningResponseDTO cleaning = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cleaning);
    }

    @PatchMapping("/{id}/updateEndDatetime")
    public ResponseEntity<CleaningResponseDTO> updateEndDatetime(@PathVariable UUID id, @RequestBody @Valid CleaningEndDTO dto) {
        CleaningResponseDTO response = service.updateEndDatetime(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
