package com.example.loja.estado;

public class PedidoCancelado implements EstadoPedido {

    @Override
    public String getNome() {
        return "Cancelado";
    }
}