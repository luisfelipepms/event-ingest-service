package com.felipesilva.event_ingest_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.felipesilva.event_ingest_service.domain.dto.CleaningDTO;
import com.felipesilva.event_ingest_service.domain.entity.Cleaning;
import com.felipesilva.event_ingest_service.domain.entity.User;
import com.felipesilva.event_ingest_service.domain.mapper.CleaningMapper;
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

    public Cleaning save(CleaningDTO dto){

        User user = userRepository.findById(dto.getUser_id().toString())
        .orElseThrow(() -> new RuntimeException("User not found"));
        Cleaning cleaning = mapper.toEntity(dto);
        cleaning.setUser(user);
        Cleaning saved = repository.save(cleaning);

        return saved;

    }

    public List<Cleaning> listAll(){
        return repository.findAll();
    }
}
