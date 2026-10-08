package com.example.loja.model;

import com.example.loja.desconto.EstrategiaDesconto;
import com.example.loja.desconto.SemDesconto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrinho {

    private final List<ItemCarrinho> itens = new ArrayList<>();
    private EstrategiaDesconto estrategiaDesconto = new SemDesconto();

    public void adicionarJogo(Jogo jogo) {
        if (jogo == null) {
            throw new IllegalArgumentException("O jogo é obrigatório.");
        }
        for (ItemCarrinho item : itens) {
            if (item.getJogo().equals(jogo)) {
                item.aumentarQuantidade(1);
                return;
            }
        }
        itens.add(new ItemCarrinho(jogo, 1));
    }

    public void removerJogo(Jogo jogo) {
        itens.removeIf(item -> item.getJogo().equals(jogo));
    }

    public void limpar() {
        itens.clear();
    }

    public boolean isVazio() {
        return itens.isEmpty();
    }

    public List<ItemCarrinho> getItens() {
        return Collections.unmodifiableList(itens);
    }

    // ---- Strategy ----

    public void setEstrategiaDesconto(EstrategiaDesconto estrategiaDesconto) {
        if (estrategiaDesconto == null) {
            throw new IllegalArgumentException("A estratégia de desconto é obrigatória.");
        }
        this.estrategiaDesconto = estrategiaDesconto;
    }

    public EstrategiaDesconto getEstrategiaDesconto() {
        return estrategiaDesconto;
    }

    // ---- Cálculos ----

    public BigDecimal getSubtotal() {
        return itens.stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDesconto() {
        BigDecimal desconto = estrategiaDesconto.calcularDesconto(getItens());
        BigDecimal subtotal = getSubtotal();
        if (desconto.compareTo(subtotal) > 0) {
            desconto = subtotal;
        }
        return desconto.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {
        return getSubtotal().subtract(getDesconto()).setScale(2, RoundingMode.HALF_UP);
    }
}