package com.example.loja.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Jogo {

    private final int id;
    private final String titulo;
    private final BigDecimal precoOriginal;
    private final BigDecimal precoPromocional; // null = sem promoção
    private final String genero;
    private final String capa;
    private final String descricao;
    private final String requisitosSistema;

    public Jogo(int id, String titulo, BigDecimal preco, String genero, String capa,
                String descricao, String requisitosSistema, BigDecimal precoPromocional) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título do jogo é obrigatório.");
        }
        if (preco == null || preco.signum() < 0) {
            throw new IllegalArgumentException("O preço não pode ser nulo ou negativo.");
        }
        if (precoPromocional != null
                && (precoPromocional.signum() < 0 || precoPromocional.compareTo(preco) >= 0)) {
            throw new IllegalArgumentException("O preço promocional deve ser menor que o preço original.");
        }
        this.id = id;
        this.titulo = titulo;
        this.precoOriginal = preco;
        this.precoPromocional = precoPromocional;
        this.genero = genero;
        this.capa = capa;
        this.descricao = descricao == null ? "" : descricao;
        this.requisitosSistema = requisitosSistema == null ? "" : requisitosSistema;
    }

    // Construtor simples (sem promoção, descrição e requisitos)
    public Jogo(int id, String titulo, BigDecimal preco, String genero, String capa) {
        this(id, titulo, preco, genero, capa, "", "", null);
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public String getCapa() { return capa; }
    public String getDescricao() { return descricao; }
    public String getRequisitosSistema() { return requisitosSistema; }

    public boolean isEmPromocao() { return precoPromocional != null; }

    public BigDecimal getPrecoOriginal() { return precoOriginal; }

    /** Preço efetivo: o promocional, se houver; senão o original. */
    public BigDecimal getPreco() {
        return isEmPromocao() ? precoPromocional : precoOriginal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jogo)) return false;
        return id == ((Jogo) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() { return titulo + " (R$ " + getPreco() + ")"; }
}