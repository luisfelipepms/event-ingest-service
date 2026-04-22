package com.felipesilva.event_ingest_service.auth;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.felipesilva.event_ingest_service.domain.entity.RefreshToken;
import com.felipesilva.event_ingest_service.domain.entity.User;
import com.felipesilva.event_ingest_service.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;


@Service
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private final RefreshTokenRepository repository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository repository, JwtService jwtService){
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @Transactional
    public RefreshToken create(User user){
        repository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshExpiration));

        return repository.save(refreshToken);
    }

    public String refresh(String token){
        RefreshToken refreshToken = repository.findByToken(token).orElseThrow(() -> new RuntimeException("Refresh token Inválido."));

        if(refreshToken.getExpiresAt().isBefore(Instant.now())){
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expirado.");
        }

        return jwtService.generateToken(refreshToken.getUser());
    }

}
