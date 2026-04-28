package com.felipesilva.event_ingest_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningEndDTO;
import com.felipesilva.event_ingest_service.domain.dto.cleaning.CleaningResponseDTO;
import com.felipesilva.event_ingest_service.domain.entity.Cleaning;
import com.felipesilva.event_ingest_service.domain.entity.User;
import com.felipesilva.event_ingest_service.domain.mapper.CleaningMapper;
import com.felipesilva.event_ingest_service.exception.UserNotFoundexception;
import com.felipesilva.event_ingest_service.repository.CleaningRepository;
import com.felipesilva.event_ingest_service.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CleaningServiceTest {
    
    @Mock
    private CleaningRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CleaningMapper mapper;

    @InjectMocks
    CleaningService cleaningService;

    UUID user_id = UUID.randomUUID();
    CleaningDTO cleaningDTO;
    Cleaning cleaningMapeado;
    Cleaning cleaningSalvo;
    CleaningResponseDTO responseDTO;
    User user = new User();

    @BeforeEach
    void setup(){
        cleaningDTO = new CleaningDTO();
        cleaningDTO.setUser_id(user_id);
        cleaningDTO.setLocal("QUARTO 01");
        cleaningDTO.setStartDatetime(LocalDateTime.now().minusHours(1));

        cleaningMapeado = new Cleaning();
        cleaningMapeado.setUser(user);
        cleaningMapeado.setLocal("QUARTO 01");
        cleaningMapeado.setStartDatetime(LocalDateTime.now().minusHours(1));

        cleaningSalvo = new Cleaning();
        cleaningSalvo.setUser(user);
        cleaningSalvo.setLocal("QUARTO 01");
        cleaningSalvo.setStartDatetime(LocalDateTime.now().minusHours(1));

        responseDTO = new CleaningResponseDTO();
        responseDTO.setUser_id(user.getId());
        responseDTO.setLocal("QUARTO 01");
        responseDTO.setStartDateTime(LocalDateTime.now().minusHours(1));
    }

    @Test
    void saveDeveRetornarCleaningResponseDTOQuandoUsuarioExiste(){

        // "quando userRepository.findById for chamado com esse ID, retorne o user"
        when(userRepository.findById(user_id.toString())).thenReturn(Optional.of(user));

        // "quando mapper.toEntity for chamado com esse dto, retorne cleaningMapeado"
        when(mapper.toEntity(cleaningDTO)).thenReturn(cleaningMapeado);

        // "quando repository.save for chamado, retorne cleaning"
        when(repository.save(cleaningMapeado)).thenReturn(cleaningSalvo);

        // "quando mapper.toResponseDto for chamado com esse cleaning, retorne responseDto"
        when(mapper.toResponseDto(cleaningSalvo)).thenReturn(responseDTO);

        // 2. ACT — executa o método que está sendo testado
        CleaningResponseDTO result = cleaningService.save(cleaningDTO);

        // 3. ASSERT — verifica o resultado
        assertEquals(responseDTO, result);

        // garante que o save foi chamado
        verify(repository).save(cleaningMapeado);
    }

    @Test
    void saveDeveRetornarExcecaoQuandoUsuarioNaoExiste(){
        when(userRepository.findById(user_id.toString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundexception.class, ()-> cleaningService.save(cleaningDTO));
    }

    @Test
    void updateEndDatetimeDeveRetornarCleaningAtualizadoQuandoDatetimeValido(){
        UUID cleaningId = UUID.randomUUID();

        CleaningEndDTO endDTO = new CleaningEndDTO();
        endDTO.setEndDatetime(LocalDateTime.now());

        Cleaning cleaning = new Cleaning();
        cleaning.setStartDatetime(LocalDateTime.now().minusHours(1));

        Cleaning saved = new Cleaning();
        CleaningResponseDTO responseDTO = new CleaningResponseDTO();

        when(repository.findById(cleaningId)).thenReturn(Optional.of(cleaning));
        when(repository.save(cleaning)).thenReturn(saved);
        when(mapper.toResponseDto(saved)).thenReturn(responseDTO);

        CleaningResponseDTO result = cleaningService.updateEndDatetime(cleaningId, endDTO);

        assertEquals(responseDTO, result);
        verify(repository).save(cleaning);
    }

    @Test
    void updateEndDatetimeDeveLancarIllegalArgumentExceptionQuandoEndDatetimeAntesDoStart(){
        // ARRANGE
        UUID cleaningId = UUID.randomUUID();

        CleaningEndDTO endDTO = new CleaningEndDTO();
        endDTO.setEndDatetime(LocalDateTime.now().minusHours(2)); // end antes do start!

        Cleaning cleaning = new Cleaning();
        cleaning.setStartDatetime(LocalDateTime.now().minusHours(1));

        when(repository.findById(cleaningId)).thenReturn(Optional.of(cleaning));

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> cleaningService.updateEndDatetime(cleaningId, endDTO));

        // garante que o save nunca foi chamado
        verify(repository, never()).save(any());
    }
}
