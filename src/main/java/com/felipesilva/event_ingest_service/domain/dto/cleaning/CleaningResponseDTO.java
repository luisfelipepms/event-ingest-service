package com.felipesilva.event_ingest_service.domain.dto.cleaning;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleaningResponseDTO {
    private UUID id;
    private String local;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String user_id;
}
