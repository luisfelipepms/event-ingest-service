package com.felipesilva.event_ingest_service.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.felipesilva.event_ingest_service.domain.dto.EventDTO;
import com.felipesilva.event_ingest_service.domain.entity.Event;
import com.felipesilva.event_ingest_service.domain.mapper.EventMapper;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {
    
    @Test
    void deveMapearDTOParaEntity(){
        EventDTO dto = new EventDTO();
        dto.setType("CLICK");
        dto.setUserId("user-123");
        dto.setTimestamp(LocalDateTime.of(2024, 1, 15, 10, 30));

        EventMapper mapper = new EventMapper();
        Event event = mapper.toEntity(dto);

        assertThat(event.getType()).isEqualTo("CLICK");
        assertThat(event.getUserId()).isEqualTo("user-123");
        assertThat(event.getTimesTamp()).isEqualTo(LocalDateTime.of(2024, 1, 15, 10, 30));
    }

    @Test
    void idNaoDeveSerCopiadoDoDTO(){
        EventDTO dto = new EventDTO();
        dto.setType("CLICK");
        dto.setUserId("user-123");
        dto.setTimestamp(LocalDateTime.now());

        EventMapper mapper = new EventMapper();

        Event event = mapper.toEntity(dto);

        assertThat(event.getId()).isNull();
    }

}
