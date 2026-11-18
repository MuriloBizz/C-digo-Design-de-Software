package com.example.loja.service;

import com.example.loja.model.Carrinho;
import com.example.loja.model.Pedido;
import com.example.loja.model.Usuario;
import com.example.loja.observer.BibliotecaObserver;
import com.example.loja.observer.EmailObserver;
import com.example.loja.observer.LogObserver;
import com.example.loja.observer.PersistenciaPedidoObserver;
import com.example.loja.repository.PedidoRepository;

public class PedidoService {

    private final PedidoRepository pedidoRepository = new PedidoRepository();

    /**
     * Cria o pedido a partir do carrinho, grava no banco (com os itens, via cascade),
     * registra os observers e o leva a "Aguardando pagamento".
     */
    public Pedido criarPedido(Usuario usuario, Carrinho carrinho, LogObserver log) {
        Pedido pedido = new Pedido(usuario, carrinho);

        // Precisa salvar ANTES de confirmar: o histórico e o status usam o id gerado pelo banco
        pedidoRepository.salvar(pedido);           // INSERT em pedido + item_pedido

        registrarObservers(pedido, log);
        pedido.confirmar();                        // State: Criado -> Aguardando pagamento
        return pedido;
    }

    /** Observers não vão para o banco (@Transient): pedidos carregados precisam registrá-los de novo. */
    public void registrarObservers(Pedido pedido, LogObserver log) {
        pedido.adicionarObserver(new PersistenciaPedidoObserver()); // grava o novo status
        pedido.adicionarObserver(log);                              // grava o histórico
        pedido.adicionarObserver(new EmailObserver());
        pedido.adicionarObserver(new BibliotecaObserver());
    }

    public void concluir(Pedido pedido) {
        pedido.concluir();                         // State: Pago -> Concluído (dispara os observers)
    }
}
