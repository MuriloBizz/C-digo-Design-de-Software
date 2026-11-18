package com.example.loja.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "biblioteca")
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    protected Biblioteca() { }

    public Biblioteca(Usuario usuario, Jogo jogo) {
        if (usuario == null || jogo == null) {
            throw new IllegalArgumentException("Usuário e Jogo são obrigatórios na biblioteca.");
        }
        this.usuario = usuario;
        this.jogo = jogo;
    }

    public Integer getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Jogo getJogo() { return jogo; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setJogo(Jogo jogo) { this.jogo = jogo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Biblioteca)) return false;
        Biblioteca that = (Biblioteca) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(jogo, that.jogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, jogo);
    }

    @Override
    public String toString() {
        return "Biblioteca [Usuário=" + (usuario != null ? usuario.getNome() : "null") +
                ", Jogo=" + (jogo != null ? jogo.getTitulo() : "null") + "]";
    }
}