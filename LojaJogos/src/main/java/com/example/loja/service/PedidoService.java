package com.example.loja.service;

import com.example.loja.model.Carrinho;
import com.example.loja.model.Pedido;
import com.example.loja.model.Usuario;
import com.example.loja.observer.BibliotecaObserver;
import com.example.loja.observer.EmailObserver;
import com.example.loja.observer.LogObserver;

public class PedidoService {

    /** Cria o pedido a partir do carrinho, registra os observers e o leva a "Aguardando pagamento". */
    public Pedido criarPedido(Usuario usuario, Carrinho carrinho, LogObserver log) {
        Pedido pedido = new Pedido(usuario, carrinho);

        // Observer: quem reage às mudanças de estado do pedido
        pedido.adicionarObserver(log);
        pedido.adicionarObserver(new EmailObserver());
        pedido.adicionarObserver(new BibliotecaObserver());

        pedido.confirmar(); // State: Criado -> Aguardando pagamento
        return pedido;
    }
}