package com.example.loja.service;

import com.example.loja.desconto.DescontoCombo;
import com.example.loja.desconto.DescontoCupom;
import com.example.loja.desconto.DescontoPercentual;
import com.example.loja.desconto.EstrategiaDesconto;
import com.example.loja.desconto.SemDesconto;
import com.example.loja.model.Carrinho;
import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.Jogo;
import com.example.loja.model.Usuario;

import java.math.BigDecimal;

public class CarrinhoService {

    private static final BigDecimal PERCENTUAL_PADRAO = new BigDecimal("10");

    // ---- Itens ----

    /** Adiciona o jogo, a menos que o usuário já o possua. Retorna false se não adicionou. */
    public boolean adicionarJogo(Carrinho carrinho, Usuario usuario, Jogo jogo) {
        if (usuario.getBiblioteca().possui(jogo)) {
            return false;
        }
        carrinho.adicionarJogo(jogo);
        return true;
    }

    public void removerJogo(Carrinho carrinho, Jogo jogo) {
        carrinho.removerJogo(jogo);
    }

    public int quantidadeTotal(Carrinho carrinho) {
        return carrinho.getItens().stream()
                .mapToInt(ItemCarrinho::getQuantidade)
                .sum();
    }

    // ---- Strategy: escolhe qual algoritmo de desconto o carrinho usa ----

    public void removerDesconto(Carrinho carrinho) {
        carrinho.setEstrategiaDesconto(new SemDesconto());
    }

    public void aplicarDescontoPercentual(Carrinho carrinho) {
        carrinho.setEstrategiaDesconto(new DescontoPercentual(PERCENTUAL_PADRAO));
    }

    public void aplicarDescontoCombo(Carrinho carrinho) {
        carrinho.setEstrategiaDesconto(new DescontoCombo());
    }

    /** Retorna true se o cupom é válido e foi aplicado; senão remove o desconto e retorna false. */
    public boolean aplicarCupom(Carrinho carrinho, String codigo) {
        if (!DescontoCupom.cupomValido(codigo)) {
            removerDesconto(carrinho);
            return false;
        }
        carrinho.setEstrategiaDesconto(new DescontoCupom(codigo));
        return true;
    }

    public EstrategiaDesconto getEstrategiaAtual(Carrinho carrinho) {
        return carrinho.getEstrategiaDesconto();
    }
}