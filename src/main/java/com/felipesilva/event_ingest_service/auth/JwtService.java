package com.felipesilva.event_ingest_service.auth;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("roles", user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token){
        return parseClaims(token).getSubject();
    }

    public List<GrantedAuthority> extractRoles(String token) {
        List<String> roles = parseClaims(token).get("roles", List.class);
        return roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                .toList();
    }
    public boolean isTokenValid(String token, String username){
        String usernameToken = extractUsername(token);
        return usernameToken.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
