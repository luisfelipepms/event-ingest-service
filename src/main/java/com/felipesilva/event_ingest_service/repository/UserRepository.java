package com.felipesilva.event_ingest_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesilva.event_ingest_service.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByUsername(String username);
} 
