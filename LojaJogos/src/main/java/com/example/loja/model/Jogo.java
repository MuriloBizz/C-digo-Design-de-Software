package com.example.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "jogo")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(name = "preco_original", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoOriginal;

    @Column(name = "preco_promocional", precision = 10, scale = 2)
    private BigDecimal precoPromocional;   // null = sem promoção

    @Column(length = 60)
    private String genero;

    @Column(length = 120)
    private String capa;

    @Column(length = 1000)
    private String descricao;

    @Column(name = "requisitos_sistema", length = 1000)
    private String requisitosSistema;

    protected Jogo() { }                    // exigido pelo JPA

    public Jogo(String titulo, BigDecimal preco, String genero, String capa,
                String descricao, String requisitosSistema, BigDecimal precoPromocional) {
        setTitulo(titulo);
        setPrecoOriginal(preco);
        setPrecoPromocional(precoPromocional);
        this.genero = genero;
        this.capa = capa;
        this.descricao = descricao == null ? "" : descricao;
        this.requisitosSistema = requisitosSistema == null ? "" : requisitosSistema;
    }

    public Integer getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public String getCapa() { return capa; }
    public String getDescricao() { return descricao; }
    public String getRequisitosSistema() { return requisitosSistema; }
    public BigDecimal getPrecoOriginal() { return precoOriginal; }

    public boolean isEmPromocao() { return precoPromocional != null; }

    /** Preço efetivo: o promocional, se houver; senão o original. */
    public BigDecimal getPreco() {
        return isEmPromocao() ? precoPromocional : precoOriginal;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título do jogo é obrigatório.");
        }
        this.titulo = titulo;
    }

    public void setPrecoOriginal(BigDecimal preco) {
        if (preco == null || preco.signum() < 0) {
            throw new IllegalArgumentException("O preço não pode ser nulo ou negativo.");
        }
        this.precoOriginal = preco;
    }

    public void setPrecoPromocional(BigDecimal promocional) {
        if (promocional != null
                && (promocional.signum() < 0 || promocional.compareTo(precoOriginal) >= 0)) {
            throw new IllegalArgumentException("O preço promocional deve ser menor que o original.");
        }
        this.precoPromocional = promocional;
    }

    public void setGenero(String genero) { this.genero = genero; }
    public void setCapa(String capa) { this.capa = capa; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setRequisitosSistema(String requisitos) { this.requisitosSistema = requisitos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jogo)) return false;
        Jogo outro = (Jogo) o;
        return id != null && id.equals(outro.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();   // estável antes e depois de receber o id
    }

    @Override
    public String toString() { return titulo + " (R$ " + getPreco() + ")"; }
}