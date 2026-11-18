package com.example.loja.demo;

import com.example.loja.model.Jogo;
import com.example.loja.repository.JogoRepository;
import com.example.loja.util.JPAUtil;

import java.math.BigDecimal;

public class TesteJogo {
    public static void main(String[] args) {
        JogoRepository repo = new JogoRepository();

        Jogo jogo = repo.salvar(new Jogo("Jogo de teste", new BigDecimal("50.00"),
                "Teste", "teste.png", "Descrição", "Requisitos", null));          // Create
        System.out.println("Criado: id=" + jogo.getId());

        System.out.println("Buscado: " + repo.buscarPorId(jogo.getId()).orElseThrow()); // Read

        jogo.setTitulo("Jogo alterado");
        repo.atualizar(jogo);                                                      // Update
        System.out.println("Todos: " + repo.listar());

        System.out.println("Excluído? " + repo.excluir(jogo.getId()));              // Delete
        System.out.println("Depois de excluir: " + repo.listar());

        JPAUtil.fechar();
    }
}