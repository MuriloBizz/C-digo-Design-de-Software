package com.example.loja.observer;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.model.Pedido;

public class EmailObserver implements PedidoObserver {

    @Override
    public void atualizar(Pedido pedido, EstadoPedido estadoAnterior) {
        System.out.println("[E-MAIL] Para: " + pedido.getUsuario().getEmail()
                + " | Pedido #" + pedido.getId()
                + " agora está: " + pedido.getNomeEstado());
    }
}