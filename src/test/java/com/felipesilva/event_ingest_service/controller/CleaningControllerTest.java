package com.felipesilva.event_ingest_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.felipesilva.event_ingest_service.auth.JwtService;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningEndDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningResponseDTO;
import com.felipesilva.event_ingest_service.service.CleaningService;

@WebMvcTest(controllers = CleaningController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class CleaningControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CleaningService service;

    @MockitoBean
    private JwtService jwtService;

    private ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        
    @Test
    @WithMockUser(roles = "USER")
    void saveDeveRetornar201QuandoDadosValidos() throws Exception{
        CleaningDTO dto = new CleaningDTO();
        dto.setUser_id(UUID.randomUUID());
        dto.setLocal("Quarto 20");
        dto.setStartDatetime(LocalDateTime.now().minusMinutes(1));

        CleaningResponseDTO responseDTO = new CleaningResponseDTO();

        when(service.save(any(CleaningDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/cleanings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andDo(print())
        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER")
    void saveDeveRetornar400QuandoLocalAusente() throws Exception{
        CleaningDTO dto = new CleaningDTO();

        dto.setUser_id(UUID.randomUUID());
        dto.setStartDatetime(LocalDateTime.now().minusMinutes(1));

        mockMvc.perform(post("/cleanings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser("USER")
    void updateEndDatetimeDeveRetornar200QuandoEndDatetimeValido() throws Exception{
        UUID id = UUID.randomUUID();

        CleaningEndDTO endDTO = new CleaningEndDTO();
        endDTO.setEndDatetime(LocalDateTime.now().minusMinutes(1));

        CleaningResponseDTO responseDTO = new CleaningResponseDTO();

        when(service.updateEndDatetime(any(UUID.class), any(CleaningEndDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(patch("/cleanings/"+ id + "/updateEndDatetime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endDTO)))
            .andExpect(status().isOk());
    }
}
