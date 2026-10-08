package com.example.loja.estado;

public class PedidoConcluido implements EstadoPedido {

    @Override
    public String getNome() {
        return "Concluído";
    }
}