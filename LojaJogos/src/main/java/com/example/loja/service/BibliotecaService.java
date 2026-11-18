package com.example.loja.service;

import com.example.loja.model.Biblioteca;
import com.example.loja.model.Jogo;
import com.example.loja.repository.BibliotecaRepository;

import java.util.List;

public class BibliotecaService {

    private final BibliotecaRepository repositorio = new BibliotecaRepository();

    /** Grava a posse do jogo. Ignora jogos sem id (ainda não gravados no banco). */
    public void adicionar(Integer usuarioId, Jogo jogo) {
        if (jogo == null || jogo.getId() == null) {
            throw new IllegalArgumentException("O jogo precisa estar gravado no banco.");
        }
        repositorio.adicionar(usuarioId, jogo.getId());
    }

    public boolean possui(Integer usuarioId, Jogo jogo) {
        return jogo.getId() != null && repositorio.possuiJogo(usuarioId, jogo.getId());
    }

    public List<Jogo> listarJogos(Integer usuarioId) {
        return repositorio.listarJogosDoUsuario(usuarioId);
    }

    public List<Biblioteca> listarRegistros(Integer usuarioId) {
        return repositorio.listarPorUsuario(usuarioId);
    }
}