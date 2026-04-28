package com.felipesilva.event_ingest_service.domain.dto.cleaning;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleaningEndDTO {
    
    @NotNull
    @PastOrPresent
    private LocalDateTime endDatetime;
}
