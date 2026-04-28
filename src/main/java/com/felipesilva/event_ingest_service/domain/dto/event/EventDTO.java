package com.felipesilva.event_ingest_service.domain.dto.event;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class EventDTO {
    private UUID id;
    
    @NotBlank
    @NotNull
    private String type;

    @NotBlank
    @Size(min = 3)
    private String userId;

    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;

    public void setType(String type){
        this.type = type;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }

    public UUID getId(){
        return this.id;
    }

    public String getType(){
        return this.type;
    }

    public String getUserId(){
        return this.userId;
    }

    public LocalDateTime getTimesTamp(){
        return this.timestamp;
    }
}
