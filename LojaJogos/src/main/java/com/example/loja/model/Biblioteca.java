package com.example.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(name = "biblioteca",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "jogo_id"}))
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    @Column(name = "adquirido_em", nullable = false)
    private LocalDateTime adquiridoEm;

    protected Biblioteca() { }                 // exigido pelo JPA

    public Biblioteca(Usuario usuario, Jogo jogo) {
        if (usuario == null || jogo == null) {
            throw new IllegalArgumentException("Usuário e jogo são obrigatórios.");
        }
        this.usuario = usuario;
        this.jogo = jogo;
        this.adquiridoEm = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Jogo getJogo() { return jogo; }
    public LocalDateTime getAdquiridoEm() { return adquiridoEm; }

    public void setAdquiridoEm(LocalDateTime adquiridoEm) {
        if (adquiridoEm == null) {
            throw new IllegalArgumentException("A data de aquisição é obrigatória.");
        }
        this.adquiridoEm = adquiridoEm;
    }

    @Override
    public String toString() {
        return usuario.getNome() + " possui " + jogo.getTitulo();
    }
}