package com.example.loja.model;

import java.math.BigDecimal;

public class ItemCarrinho {

    private final Jogo jogo;
    private int quantidade;

    public ItemCarrinho(Jogo jogo, int quantidade) {
        if (jogo == null) {
            throw new IllegalArgumentException("O jogo é obrigatório.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.jogo = jogo;
        this.quantidade = quantidade;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void aumentarQuantidade(int adicional) {
        if (adicional <= 0) {
            throw new IllegalArgumentException("O valor a adicionar deve ser maior que zero.");
        }
        this.quantidade += adicional;
    }

    public void diminuirQuantidade(int remover) {
        if (remover <= 0 || remover >= quantidade) {
            throw new IllegalArgumentException("Quantidade inválida. Para remover o item, use removerJogo no carrinho.");
        }
        this.quantidade -= remover;
    }

    public BigDecimal getSubtotal() {
        return jogo.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return quantidade + "x " + jogo.getTitulo() + " = R$ " + getSubtotal();
    }
}