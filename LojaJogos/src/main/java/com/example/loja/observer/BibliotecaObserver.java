package com.example.loja.observer;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.estado.PedidoPago;
import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.Pedido;

public class BibliotecaObserver implements PedidoObserver {

    @Override
    public void atualizar(Pedido pedido, EstadoPedido estadoAnterior) {
        if (pedido.getEstado() instanceof PedidoPago) {
            for (ItemCarrinho item : pedido.getItens()) {
                pedido.getUsuario().getBiblioteca().adicionar(item.getJogo());
            }
        }
    }
}