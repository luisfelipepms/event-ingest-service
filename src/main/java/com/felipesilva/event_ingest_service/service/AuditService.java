package com.felipesilva.event_ingest_service.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.felipesilva.event_ingest_service.domain.entity.Event;

@Service
public class AuditService {

    @Async("taskExecutor")
    public CompletableFuture<Void> resgistrarAuditoria(Event event){
        System.out.println("[AUDIT] Thread: "
            + Thread.currentThread().getName()
            + " | Evento: " + event.getType());
        return CompletableFuture.completedFuture(null);
    }
}
