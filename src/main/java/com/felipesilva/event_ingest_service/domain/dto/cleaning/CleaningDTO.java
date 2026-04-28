package com.felipesilva.event_ingest_service.domain.dto.cleaning;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleaningDTO {
    private UUID id;

    @NotNull
    private UUID user_id;

    @NotBlank
    @NotNull
    @Size(min = 3)
    private String local;

    @NotNull
    @PastOrPresent
    private LocalDateTime startDatetime;

    @PastOrPresent
    private LocalDateTime endDatetime;
}
