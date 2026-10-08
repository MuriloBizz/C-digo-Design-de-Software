package com.example.loja.model;

public class Usuario {

    private final String nome;
    private final String email;
    private final Biblioteca biblioteca = new Biblioteca();

    public Usuario(String nome, String email) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.nome = nome;
        this.email = email;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Biblioteca getBiblioteca() { return biblioteca; }
}