package com.example.loja.estado;

import com.example.loja.model.Pedido;

public class PedidoAguardandoPagamento implements EstadoPedido {

    @Override
    public String getNome() {
        return "Aguardando pagamento";
    }

    @Override
    public void pagar(Pedido pedido) {
        pedido.setEstado(new PedidoPago());
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado(new PedidoCancelado());
    }
}