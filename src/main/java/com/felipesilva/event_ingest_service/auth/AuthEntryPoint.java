package com.felipesilva.event_ingest_service.auth;

import java.io.IOException;

import com.felipesilva.event_ingest_service.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint { // trata o 401 Unauthorized

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        error.setMessage("Não autorizado: token ausente ou inválido");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}