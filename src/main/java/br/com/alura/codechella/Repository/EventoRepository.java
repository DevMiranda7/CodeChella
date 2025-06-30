package br.com.alura.codechella.Repository;

import br.com.alura.codechella.Entity.Evento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
}
