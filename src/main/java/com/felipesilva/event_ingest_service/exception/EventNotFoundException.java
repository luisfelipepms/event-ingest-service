package com.felipesilva.event_ingest_service.exception;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {
    private final UUID id;
    public EventNotFoundException(UUID id){
        super("Evento não encontrado: " + id);
        this.id = id;
    }

    public UUID getId(){
        return id;
    }

}
