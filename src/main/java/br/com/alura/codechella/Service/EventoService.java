package br.com.alura.codechella.Service;

import br.com.alura.codechella.DTO.EventoDTO;
import br.com.alura.codechella.Enums.TipoEvento;
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

    public Mono<EventoDTO> cadastrar(EventoDTO eventoDTO) {
        return eventoRepository.save(eventoDTO.toEntity()).map(EventoDTO::toDto);
    }

    public Mono<Void> deleteEvento(Long id) {
        return eventoRepository.findById(id).flatMap(eventoRepository::delete);
    }


    public Mono<EventoDTO> alterarEvento(Long idEvento, EventoDTO eventoDTO) {
        return eventoRepository.findById(idEvento).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não encontrado")))
                .flatMap(evento -> {
                    evento.setTipo(eventoDTO.tipoEvento());
                    evento.setNome(eventoDTO.nome());
                    evento.setData(eventoDTO.data());
                    evento.setDescricao(eventoDTO.descricao());
                    return eventoRepository.save(evento);
                }).map(EventoDTO::toDto);
    }


    public Flux<EventoDTO> obterPortipo(String tipo) {
        TipoEvento tipoEvento = TipoEvento.valueOf(tipo.toUpperCase());
        return eventoRepository.findByTipo(tipoEvento).map(EventoDTO::toDto);
    }
}
