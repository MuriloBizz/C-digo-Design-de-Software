package com.example.loja.util;

import com.example.loja.model.Carrinho;
import com.example.loja.model.Pedido;
import com.example.loja.model.Usuario;

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
}