package br.com.alura.codechella.Repository;

import br.com.alura.codechella.Entity.Evento;
import br.com.alura.codechella.Enums.TipoEvento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {

    Flux<Evento> findByTipo(TipoEvento tipoEvento);
}
