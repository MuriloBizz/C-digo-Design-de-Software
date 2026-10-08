package com.example.loja.estado;

import com.example.loja.model.Pedido;

public class PedidoCriado implements EstadoPedido {

    @Override
    public String getNome() {
        return "Criado";
    }

    @Override
    public void confirmar(Pedido pedido) {
        pedido.setEstado(new PedidoAguardandoPagamento());
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado(new PedidoCancelado());
    }
}