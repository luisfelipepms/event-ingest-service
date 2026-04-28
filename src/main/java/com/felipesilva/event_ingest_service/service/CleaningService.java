package com.felipesilva.event_ingest_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningEndDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningResponseDTO;
import com.felipesilva.event_ingest_service.domain.entity.Cleaning;
import com.felipesilva.event_ingest_service.domain.entity.User;
import com.felipesilva.event_ingest_service.domain.mapper.CleaningMapper;
import com.felipesilva.event_ingest_service.exception.UserNotFoundexception;
import com.felipesilva.event_ingest_service.repository.CleaningRepository;
import com.felipesilva.event_ingest_service.repository.UserRepository;

@Service
public class CleaningService {
    
    private final CleaningRepository repository;
    private final UserRepository userRepository;
    private final CleaningMapper mapper;

    public CleaningService(CleaningRepository repository, CleaningMapper mapper, UserRepository userRepository){
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public CleaningResponseDTO save(CleaningDTO dto){

        User user = userRepository.findById(dto.getUser_id().toString())
        .orElseThrow(() -> new UserNotFoundexception());
        Cleaning cleaning = mapper.toEntity(dto);
        cleaning.setUser(user);
        Cleaning saved = repository.save(cleaning);

        CleaningResponseDTO response = mapper.toResponseDto(saved);

        return response;

    }

    public List<Cleaning> listAll(){
        return repository.findAll();
    }

    public CleaningResponseDTO updateEndDatetime(UUID id, CleaningEndDTO dto){
        Cleaning cleaning = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Limpeza não encontrada!"));

        if(dto.getEndDatetime().isBefore(cleaning.getStartDatetime())){
            throw new IllegalArgumentException("Data final não pode ser anterior a data inicial!");
        }

        cleaning.setEndDateTime(dto.getEndDatetime());

        Cleaning updated = repository.save(cleaning);

        CleaningResponseDTO response = mapper.toResponseDto(updated);

        return response;
        
    }
}
