package com.felipesilva.event_ingest_service.auth;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.felipesilva.event_ingest_service.exception.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler{ //trata o 403 Forbidden
    
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpServletResponse.SC_FORBIDDEN);
        error.setMessage("Acesso negado: você não tem permissão para acessar este recurso");

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
