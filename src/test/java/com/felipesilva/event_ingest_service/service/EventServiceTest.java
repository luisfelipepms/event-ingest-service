package com.felipesilva.event_ingest_service.service;

import com.felipesilva.event_ingest_service.repository.EventRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.felipesilva.event_ingest_service.domain.dto.EventDTO;
import com.felipesilva.event_ingest_service.domain.entity.Event;
import com.felipesilva.event_ingest_service.domain.mapper.EventMapper;
import com.felipesilva.event_ingest_service.exception.EventNotFoundException;
import com.felipesilva.event_ingest_service.exception.TypeNotFoundException;


@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    
    @Mock
    private EventRepository repository;

    @Mock
    private EventMapper mapper;

    @InjectMocks
    private EventService service;

    private EventDTO dtoValido;
    private Event eventConvertido;
    private Event eventSalvo;

    @BeforeEach
    void setup(){
        dtoValido = new EventDTO();
        dtoValido.setType("CLICK");
        dtoValido.setUserId("user-123");
        dtoValido.setTimestamp(LocalDateTime.now().minusMinutes(5));

        eventConvertido = new Event();    // o que o mapper vai "fingir" que retornou
        eventConvertido.setType("CLICK");
        eventConvertido.setUserId("user-123");

        eventSalvo = new Event();
        eventSalvo.setType("CLICK");
        eventSalvo.setUserId("user-123");
        eventSalvo.setTimestamp(dtoValido.getTimesTamp());
    }

    @Test
    void deveSalvarEventoComDadosValidos(){
        when(mapper.toEntity(dtoValido)).thenReturn(eventConvertido);
        when(repository.save(any(Event.class))).thenReturn(eventSalvo);

        Event resultado = service.save(dtoValido);

        assertThat(resultado.getType()).isEqualTo("CLICK");
        assertThat(resultado.getUserId()).isEqualTo("user-123");
    }

    @Test
    void deveRetornarEventoPorId(){
        UUID id = UUID.randomUUID();
        Event event = new Event();
        event.setType("VIEW");
        
        when(repository.findById(id)).thenReturn(Optional.of(event));

        Event resultado = service.getEventById(id);

        assertThat(resultado.getType()).isEqualTo("VIEW");
    }

    @Test
    void deveLancarEventNotFoundQuandoIdNaoExiste(){
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->service.getEventById(id))
            .isInstanceOf(EventNotFoundException.class);

    }

    @Test
    void deveRetornarListaQuandoTipoExiste(){
        Event event = new Event();
        event.setType("CLICK");

        when(repository.findByType("CLICK")).thenReturn(List.of(event));

        List<Event> resultado = service.findByType("CLICK");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getType()).isEqualTo("CLICK");
    }

    @Test
    void deveLancarExcecaoQuandoTipoNaoForEncontrado(){
        when(repository.findByType("CLICK")).thenReturn(List.of());

        assertThatThrownBy(() -> service.findByType("CLICK"))
            .isInstanceOf(TypeNotFoundException.class);
    }
}
