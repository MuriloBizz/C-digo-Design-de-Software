package com.example.loja.observer;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.model.Pedido;

public interface PedidoObserver {

    void atualizar(Pedido pedido, EstadoPedido estadoAnterior);
}