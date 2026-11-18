package com.example.loja.catalogo;

import com.example.loja.model.cruds.Jogocrud;
import com.example.loja.model.Jogo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogoJogos {

    private static CatalogoJogos instancia;
    private final Jogocrud jogocrud = new Jogocrud();

    private CatalogoJogos() {
        // Construtor privado para o padrão Singleton
    }

    public static synchronized CatalogoJogos getInstancia() {
        if (instancia == null) {
            instancia = new CatalogoJogos();
        }
        return instancia;
    }

    // Carrega os jogos diretamente do banco de dados via JPA
    public List<Jogo> listar() {
        return jogocrud.listarTodos();
    }

    public Optional<Jogo> buscarPorId(int id) {
        Jogo jogo = jogocrud.buscarPorId(id);
        return Optional.ofNullable(jogo);
    }

    public List<Jogo> buscarPorTitulo(String consulta) {
        String termo = consulta == null ? "" : consulta.trim().toLowerCase();
        return jogocrud.listarTodos().stream()
                .filter(j -> j.getTitulo().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }
}