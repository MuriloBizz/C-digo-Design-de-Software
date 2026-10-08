package com.example.loja.estado;

import com.example.loja.model.Pedido;

public class PedidoPago implements EstadoPedido {

    @Override
    public String getNome() {
        return "Pago";
    }

    @Override
    public void concluir(Pedido pedido) {
        pedido.setEstado(new PedidoConcluido());
    }
}