package com.example.loja.estado;

import com.example.loja.model.Pedido;

public interface EstadoPedido {

    String getNome();

    default void confirmar(Pedido pedido) {
        throw operacaoInvalida("confirmar");
    }

    default void pagar(Pedido pedido) {
        throw operacaoInvalida("pagar");
    }

    default void concluir(Pedido pedido) {
        throw operacaoInvalida("concluir");
    }

    default void cancelar(Pedido pedido) {
        throw operacaoInvalida("cancelar");
    }

    private IllegalStateException operacaoInvalida(String acao) {
        return new IllegalStateException(
                "Não é possível " + acao + " um pedido no estado: " + getNome());
    }
}