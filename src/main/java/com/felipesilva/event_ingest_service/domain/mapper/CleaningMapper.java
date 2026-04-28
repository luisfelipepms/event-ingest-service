package com.felipesilva.event_ingest_service.domain.mapper;

import org.springframework.stereotype.Component;

import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningResponseDTO;
import com.felipesilva.event_ingest_service.domain.entity.Cleaning;

@Component
public class CleaningMapper {
    public Cleaning toEntity(CleaningDTO dto){
        Cleaning cleaning = new Cleaning();
        cleaning.setId(dto.getId());
        cleaning.setLocal(dto.getLocal());
        cleaning.setStartDatetime(dto.getStartDatetime());
        cleaning.setEndDateTime(dto.getEndDatetime());
        return cleaning;
    }

    public CleaningResponseDTO toResponseDto(Cleaning cleaning){
        CleaningResponseDTO dto = new CleaningResponseDTO();
        dto.setLocal(cleaning.getLocal());
        dto.setId(cleaning.getId());
        dto.setStartDateTime(cleaning.getStartDatetime());
        dto.setEndDateTime(cleaning.getEndDateTime());
        dto.setUser_id(cleaning.getUser().getId());
        return dto;
    }
}
