package com.felipesilva.event_ingest_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.felipesilva.event_ingest_service.domain.entity.RefreshToken;
import com.felipesilva.event_ingest_service.domain.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String>{
    Optional<RefreshToken> findByToken(String token);
    
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
    void deleteByUser(User user);
}
