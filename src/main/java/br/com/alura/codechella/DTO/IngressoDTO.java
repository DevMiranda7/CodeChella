package br.com.alura.codechella.DTO;

import br.com.alura.codechella.Entity.Ingresso;
import br.com.alura.codechella.Enums.TipoEvento;
import br.com.alura.codechella.Enums.TipoIngresso;

import java.time.LocalDate;

public record IngressoDTO(
        Long id,
        Long eventoId,
        TipoIngresso tipo,
        Double valor,
        int total
) {

    public static IngressoDTO ToDto(Ingresso ingresso){
        return new IngressoDTO(ingresso.getId(), ingresso.getEventoId(),
                ingresso.getTipoIngresso(), ingresso.getValor(), ingresso.getTotal());
    }

    public Ingresso toEntity(){
        Ingresso ingresso = new Ingresso();
        ingresso.setId(this.id);
        ingresso.setEventoId(this.eventoId);
        ingresso.setTipoIngresso(this.tipo);
        ingresso.setValor(this.valor);
        ingresso.setTotal(this.total);
        return ingresso;
    }




}
