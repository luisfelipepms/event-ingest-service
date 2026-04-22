package com.felipesilva.event_ingest_service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipesilva.event_ingest_service.domain.entity.RefreshToken;
import com.felipesilva.event_ingest_service.domain.entity.Role;
import com.felipesilva.event_ingest_service.domain.entity.User;

import com.felipesilva.event_ingest_service.repository.UserRepository;
import com.felipesilva.event_ingest_service.service.UserDetailServiceImpl;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImpl userDetailService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, UserDetailServiceImpl userDetailService, JwtService jwtService, RefreshTokenService refreshTokenService, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO login(AuthDTO authDTO){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
        );

        User user = (User) userDetailService.loadUserByUsername(authDTO.getUsername());

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);

        return new AuthResponseDTO(accessToken, refreshToken.getToken());
    }

    public void register(RegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // ← hash aqui
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public String refresh(String token){
        return refreshTokenService.refresh(token);
    }
}
