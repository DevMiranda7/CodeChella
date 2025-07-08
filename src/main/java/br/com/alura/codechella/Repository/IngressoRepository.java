package br.com.alura.codechella.Repository;

import br.com.alura.codechella.DTO.IngressoDTO;
import br.com.alura.codechella.Entity.Ingresso;
import br.com.alura.codechella.Enums.TipoEvento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IngressoRepository extends ReactiveCrudRepository<Ingresso, Long> {


}
