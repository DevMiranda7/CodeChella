package br.com.alura.codechella.Controller;

import br.com.alura.codechella.DTO.EventoDTO;
import br.com.alura.codechella.Entity.Evento;
import br.com.alura.codechella.Repository.EventoRepository;
import br.com.alura.codechella.Service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    @Autowired

    private EventoService service;

    @GetMapping
    public Flux<EventoDTO> obterTodos(){
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    public Mono<EventoDTO> obterPorId(@PathVariable Long id){
        return service.obterPorId(id);
    }

}
