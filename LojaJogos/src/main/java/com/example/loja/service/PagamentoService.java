package com.example.loja.service;

import com.example.loja.model.MetodoPagamento;
import com.example.loja.model.Pagamento;
import com.example.loja.model.Pedido;

public class PagamentoService {

    public Pagamento pagar(Pedido pedido, MetodoPagamento metodo) {
        Pagamento pagamento = new Pagamento(pedido, metodo);
        pagamento.aprovar();   // simulação: sempre aprova
        pedido.pagar();        // State: Aguardando pagamento -> Pago (dispara os observers)
        return pagamento;
    }

    public void cancelar(Pedido pedido) {
        pedido.cancelar();     // State: Aguardando pagamento -> Cancelado
    }
}