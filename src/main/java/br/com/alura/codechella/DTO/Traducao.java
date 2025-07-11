package br.com.alura.codechella.DTO;

import java.util.List;

public record Traducao(List<Texto> translations) {

    public String getTexto(){
        return translations.get(0).text();
    }
}
