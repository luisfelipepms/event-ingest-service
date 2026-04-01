package com.felipesilva.event_ingest_service.domain.mapper;

import org.springframework.stereotype.Component;

import com.felipesilva.event_ingest_service.domain.dto.EventDTO;
import com.felipesilva.event_ingest_service.domain.entity.Event;

@Component
public class EventMapper {
    public Event toEntity(EventDTO dto){
        Event event = new Event();
        event.setType(dto.getType());
        event.setUserId(dto.getUserId());
        event.setTimestamp(dto.getTimesTamp());
        return event;
    }
}
