package br.com.alura.codechella.Repository;

import br.com.alura.codechella.Entity.Venda;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VendaRepository extends ReactiveCrudRepository<Venda, Long> {
}