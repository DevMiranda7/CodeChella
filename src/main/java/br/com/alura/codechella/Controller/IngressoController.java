package br.com.alura.codechella.Controller;

import br.com.alura.codechella.DTO.CompraDTO;
import br.com.alura.codechella.DTO.EventoDTO;
import br.com.alura.codechella.DTO.IngressoDTO;
import br.com.alura.codechella.Entity.Ingresso;
import br.com.alura.codechella.Service.IngressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {


    private final IngressoService ingressoService;

    private final Sinks.Many<IngressoDTO> ingressoSinks;

    public IngressoController(IngressoService ingressoService){
        this.ingressoService = ingressoService;
        this.ingressoSinks = Sinks.many().multicast().onBackpressureBuffer();
    }



    @GetMapping
    public Flux<IngressoDTO> todosOsIngressos(){
        return ingressoService.todosOsIngresos();
    }

    @GetMapping(value = "{id}")
    public Mono<IngressoDTO> obterPorId(@PathVariable  Long id){
        return ingressoService.buscarPorId(id);
    }

    @PostMapping
    public Mono<IngressoDTO> cadastrar(@RequestBody IngressoDTO ingressoDTO){
        return ingressoService.cadastrarIngresso(ingressoDTO);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletar(@PathVariable Long id){
        return ingressoService.excluir(id);
    }

    @PutMapping("/{id}")
    public Mono<IngressoDTO> atualizarIngresso(@PathVariable Long id, @RequestBody IngressoDTO ingressoDTO){
        return ingressoService.atualizar(id,ingressoDTO);
    }

    @PostMapping("/compra")
    public Mono<IngressoDTO> comprar(@RequestBody CompraDTO dto) {
        return ingressoService.comprar(dto)
                .doOnSuccess(i -> ingressoSinks.tryEmitNext(i));
    }

    @GetMapping(value = "/{id}/disponivel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<IngressoDTO> totalDisponivel(@PathVariable Long id) {
        return Flux.merge(ingressoService.buscarPorId(id), ingressoSinks.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }






}
