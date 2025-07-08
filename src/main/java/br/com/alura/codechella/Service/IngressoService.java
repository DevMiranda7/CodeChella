package br.com.alura.codechella.Service;

import br.com.alura.codechella.DTO.CompraDTO;
import br.com.alura.codechella.DTO.EventoDTO;
import br.com.alura.codechella.DTO.IngressoDTO;
import br.com.alura.codechella.Entity.Evento;
import br.com.alura.codechella.Entity.Ingresso;
import br.com.alura.codechella.Entity.Venda;
import br.com.alura.codechella.Enums.TipoEvento;
import br.com.alura.codechella.Repository.IngressoRepository;
import br.com.alura.codechella.Repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;
    @Autowired
    private VendaRepository vendaRepository;

    public Flux<IngressoDTO> todosOsIngresos() {
        return ingressoRepository.findAll()
                .map(IngressoDTO::ToDto);
    }

    public Mono<IngressoDTO> buscarPorId(Long id){
        return ingressoRepository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))).map(IngressoDTO::ToDto);
    }


    public Mono<IngressoDTO> cadastrarIngresso(IngressoDTO ingressoDTO) {
        return ingressoRepository.save(ingressoDTO.toEntity()).map(IngressoDTO::ToDto);
    }

    public Mono<Void> excluir(Long id){
        return ingressoRepository.findById(id).flatMap(ingressoRepository::delete);
    }

    public Mono<IngressoDTO> atualizar(Long id, IngressoDTO ingressoDTO){
        return ingressoRepository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não existe")))
                .flatMap(ingresso -> {
                    ingresso.setEventoId(ingressoDTO.eventoId());
                    ingresso.setTipoIngresso(ingressoDTO.tipo());
                    ingresso.setValor(ingressoDTO.valor());
                    ingresso.setTotal(ingressoDTO.total());
                    return ingressoRepository.save(ingresso);
                })
                .map(IngressoDTO::ToDto);
    }

    @Transactional
    public Mono<IngressoDTO> comprar(CompraDTO dto) {
        return ingressoRepository.findById(dto.ingressoId())
                .flatMap(ingresso -> {
                    Venda venda = new Venda();
                    venda.setIngressoId(ingresso.getId());
                    venda.setTotal(dto.total());
                    return vendaRepository.save(venda).then(Mono.defer(() -> {
                        ingresso.setTotal(ingresso.getTotal() - dto.total());
                        return ingressoRepository.save(ingresso);
                    }));
                }).map(IngressoDTO::ToDto);
    }
}

