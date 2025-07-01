package br.com.alura.codechella.Service;

import br.com.alura.codechella.DTO.EventoDTO;
import br.com.alura.codechella.Repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Flux<EventoDTO> obterTodos(){
        return eventoRepository.findAll()
                .map(EventoDTO::toDto);
    }

    public Mono<EventoDTO> obterPorId(Long id) {
        return eventoRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoDTO::toDto);
    }
}
