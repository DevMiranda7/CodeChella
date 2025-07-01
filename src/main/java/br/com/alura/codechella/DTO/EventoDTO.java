package br.com.alura.codechella.DTO;

import br.com.alura.codechella.Entity.Evento;
import br.com.alura.codechella.Enums.TipoEvento;

import java.time.LocalDate;

public record EventoDTO(
        Long id,
        TipoEvento tipoEvento,
        String nome,
        LocalDate data,
        String descricao) {



    public static EventoDTO toDto(Evento evento){
        return new EventoDTO(evento.getId(),evento.getTipo(), evento.getNome(),evento.getData(), evento.getDescricao());
    }
}
