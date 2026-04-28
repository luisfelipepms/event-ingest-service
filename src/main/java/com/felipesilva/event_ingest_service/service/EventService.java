package com.felipesilva.event_ingest_service.service;

import com.felipesilva.event_ingest_service.repository.EventRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.felipesilva.event_ingest_service.domain.dto.event.EventDTO;
import com.felipesilva.event_ingest_service.domain.entity.Event;
import com.felipesilva.event_ingest_service.domain.mapper.EventMapper;
import com.felipesilva.event_ingest_service.exception.EventNotFoundException;
import com.felipesilva.event_ingest_service.exception.TypeNotFoundException;

@Service
public class EventService {
    
    private final AuditService auditService;
    private final EventRepository repository;
    private final EventMapper mapper;

    public EventService(EventRepository repository, EventMapper mapper, AuditService auditService){
        this.repository = repository;
        this.mapper = mapper;
        this.auditService = auditService;
    }

    public Event save(EventDTO dto){
        Event event = mapper.toEntity(dto);
        Event saved =  repository.save(event);

        auditService.resgistrarAuditoria(event);

        return saved;
    }

    public List<Event> listAll(){
        return repository.findAll();
    }

    public Event getEventById(UUID id){
        return repository.findById(id).orElseThrow(()->new EventNotFoundException(id));
    }

    public List<Event> findByType(String type){
        List<Event> lista;
        if(type == null){
            return repository.findAll();
        }else{
            lista = repository.findByType(type);
            if(lista.isEmpty()){
                throw new TypeNotFoundException();
            }
            return lista;
        }
    }

}
