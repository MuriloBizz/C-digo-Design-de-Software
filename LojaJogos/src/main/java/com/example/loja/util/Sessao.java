package com.example.loja.util;

import com.example.loja.model.Carrinho;
import com.example.loja.model.Pedido;
import com.example.loja.model.Usuario;
import com.example.loja.model.cruds.Usuariocrud;
import com.example.loja.observer.LogObserver;

public final class Sessao {

    private static Usuario usuario;
    private static Carrinho carrinho = new Carrinho();
    private static Pedido pedidoAtual;

    private Sessao() { }

    // Busca o usuário padrão no banco; se ainda não existir, cadastra
    public static Usuario getUsuario() {
        if (usuario == null) {
            Usuariocrud crud = new Usuariocrud();
            usuario = crud.buscarPorEmail("jogador@email.com");
            if (usuario == null) {
                usuario = new Usuario("Jogador", "jogador@email.com");
                crud.salvar(usuario);   // depois do persist, o id já vem preenchido
            }
        }
        return usuario;
    }

    public static Carrinho getCarrinho() { return carrinho; }

    public static void novoCarrinho() { carrinho = new Carrinho(); }

    public static Pedido getPedidoAtual() { return pedidoAtual; }

    public static void setPedidoAtual(Pedido pedido) { pedidoAtual = pedido; }

    private static LogObserver logPedido = new LogObserver();

    public static LogObserver getLogPedido() { return logPedido; }

    public static void novoLog() { logPedido = new LogObserver(); }
}