package br.com.alura.codechella.Entity;

import br.com.alura.codechella.Enums.TipoEvento;
import br.com.alura.codechella.Enums.TipoIngresso;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("ingressos")
public class Ingresso {

    @Id
    private Long id;

    private Long eventoId;

    private TipoIngresso tipoIngresso;

    private Double valor;

    private int total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public TipoIngresso getTipoIngresso() {
        return tipoIngresso;
    }

    public void setTipoIngresso(TipoIngresso tipoIngresso) {
        this.tipoIngresso = tipoIngresso;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
