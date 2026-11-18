package com.example.loja.util;

import com.example.loja.model.Carrinho;
import com.example.loja.model.Pedido;
import com.example.loja.model.Usuario;
import com.example.loja.observer.LogObserver;
import com.example.loja.repository.UsuarioRepository;

public final class Sessao {

    // Usuário carregado do banco (criado na primeira execução)
    private static final Usuario USUARIO = carregarUsuario();

    private static Carrinho carrinho = new Carrinho();
    private static Pedido pedidoAtual;
    private static LogObserver logPedido = new LogObserver();

    private Sessao() { }

    private static Usuario carregarUsuario() {
        UsuarioRepository repositorio = new UsuarioRepository();
        return repositorio.buscarPorEmail("jogador@email.com")
                .orElseGet(() -> repositorio.salvar(new Usuario("Jogador", "jogador@email.com")));
    }

    public static Usuario getUsuario() { return USUARIO; }

    public static Carrinho getCarrinho() { return carrinho; }

    public static void novoCarrinho() { carrinho = new Carrinho(); }

    public static Pedido getPedidoAtual() { return pedidoAtual; }

    public static void setPedidoAtual(Pedido pedido) { pedidoAtual = pedido; }

    public static LogObserver getLogPedido() { return logPedido; }

    public static void novoLog() { logPedido = new LogObserver(); }
}