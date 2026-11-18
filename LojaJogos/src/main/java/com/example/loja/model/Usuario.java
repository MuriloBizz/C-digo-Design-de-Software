package com.example.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // Lado inverso da relação: os registros de biblioteca deste usuário
    @OneToMany(mappedBy = "usuario")
    private List<Biblioteca> itensBiblioteca = new ArrayList<>();

    protected Usuario() { }                    // exigido pelo JPA

    public Usuario(String nome, String email) {
        setNome(nome);
        setEmail(email);
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    public List<Biblioteca> getItensBiblioteca() {
        return Collections.unmodifiableList(itensBiblioteca);
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        this.nome = nome;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.email = email;
    }

    @Override
    public String toString() { return nome + " <" + email + ">"; }
}