package com.example.loja.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Jogo {

    private final int id;
    private final String titulo;
    private final BigDecimal preco;
    private final String genero;
    private final String capa;

    public Jogo(int id, String titulo, BigDecimal preco, String genero, String capa) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título do jogo é obrigatório.");
        }
        if (preco == null || preco.signum() < 0) {
            throw new IllegalArgumentException("O preço não pode ser nulo ou negativo.");
        }
        this.id = id;
        this.titulo = titulo;
        this.preco = preco;
        this.genero = genero;
        this.capa = capa;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getGenero() {
        return genero;
    }

    public String getCapa() {
        return capa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jogo)) return false;
        Jogo jogo = (Jogo) o;
        return id == jogo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return titulo + " (R$ " + preco + ")";
    }
}