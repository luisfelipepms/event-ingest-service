package com.felipesilva.event_ingest_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesilva.event_ingest_service.domain.entity.Cleaning;

public interface CleaningRepository extends JpaRepository<Cleaning, UUID>{}
