package com.felipesilva.event_ingest_service.domain.mapper;

import org.springframework.stereotype.Component;

import com.felipesilva.event_ingest_service.domain.dto.CleaningDTO;
import com.felipesilva.event_ingest_service.domain.entity.Cleaning;

@Component
public class CleaningMapper {
    public Cleaning toEntity(CleaningDTO dto){
        Cleaning cleaning = new Cleaning();
        cleaning.setLocal(dto.getLocal());
        cleaning.setStartDatetime(dto.getStartDatetime());
        cleaning.setEndDateTime(dto.getEndDatetime());
        return cleaning;
    }
}
