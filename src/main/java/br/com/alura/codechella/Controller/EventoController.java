package br.com.alura.codechella.Controller;

import br.com.alura.codechella.DTO.EventoDTO;
import br.com.alura.codechella.Entity.Evento;
import br.com.alura.codechella.Repository.EventoRepository;
import br.com.alura.codechella.Service.EventoService;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.Duration;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    @Autowired

    private EventoService service;

    @GetMapping
    public Flux<EventoDTO> obterTodos(){
        return service.obterTodos();
    }

    @GetMapping(value = "/categoria/{tipo}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDTO> obterPorTipo(@PathVariable String tipo){
        return  Flux.from(service.obterPortipo(tipo)).delayElements(Duration.ofSeconds(4));
    }

    @GetMapping("/{id}")
    public Mono<EventoDTO> obterPorId(@PathVariable Long id){
        return service.obterPorId(id);
    }

    @PostMapping
    public Mono<EventoDTO> cadastro(@RequestBody EventoDTO eventoDTO){
        return service.cadastrar(eventoDTO);
    }

    @DeleteMapping("/{idDelete}")
    public Mono<Void> remove(@PathVariable Long idDelete){
        return service.deleteEvento(idDelete);
    }

    @PutMapping("/{idEvento}")
    public Mono<EventoDTO> alterar(@PathVariable Long idEvento, @RequestBody EventoDTO eventoDTO){
        return service.alterarEvento(idEvento, eventoDTO);
    }
}
