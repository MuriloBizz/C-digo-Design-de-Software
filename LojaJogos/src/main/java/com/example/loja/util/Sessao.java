package com.example.loja.util;

import com.example.loja.model.Carrinho;
import com.example.loja.model.Pedido;
import com.example.loja.model.Usuario;
import com.example.loja.observer.LogObserver;

public final class Sessao {

    private static final Usuario USUARIO = new Usuario("Jogador", "jogador@email.com");
    private static Carrinho carrinho = new Carrinho();
    private static Pedido pedidoAtual;

    private Sessao() { }

    public static Usuario getUsuario() { return USUARIO; }

    public static Carrinho getCarrinho() { return carrinho; }

    public static void novoCarrinho() { carrinho = new Carrinho(); }

    public static Pedido getPedidoAtual() { return pedidoAtual; }

    public static void setPedidoAtual(Pedido pedido) { pedidoAtual = pedido; }

    private static LogObserver logPedido = new LogObserver();

    public static LogObserver getLogPedido() { return logPedido; }

    public static void novoLog() { logPedido = new LogObserver(); }
}