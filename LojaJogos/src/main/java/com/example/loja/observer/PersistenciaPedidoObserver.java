package com.example.loja.observer;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.model.Pedido;
import com.example.loja.repository.PedidoRepository;

/**
 * Grava no banco o novo estado do pedido a cada transição do State.
 * Assim as classes de estado continuam sem saber que existe banco de dados.
 */
public class PersistenciaPedidoObserver implements PedidoObserver {

    private final PedidoRepository repositorio = new PedidoRepository();

    @Override
    public void atualizar(Pedido pedido, EstadoPedido estadoAnterior) {
        repositorio.atualizarStatus(pedido.getId(), pedido.getNomeEstado());
    }
}
