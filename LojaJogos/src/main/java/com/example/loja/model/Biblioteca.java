package com.example.loja.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Biblioteca {

    private final List<Jogo> jogos = new ArrayList<>();

    public void adicionar(Jogo jogo) {
        if (jogo != null && !jogos.contains(jogo)) {
            jogos.add(jogo);
        }
    }

    public boolean possui(Jogo jogo) {
        return jogos.contains(jogo);
    }

    public List<Jogo> getJogos() {
        return Collections.unmodifiableList(jogos);
    }
}