package com.example.loja.service;

import com.example.loja.model.MetodoPagamento;
import com.example.loja.model.Pagamento;
import com.example.loja.model.Pedido;
import com.example.loja.repository.PagamentoRepository;

public class PagamentoService {

    private final PagamentoRepository pagamentoRepository = new PagamentoRepository();

    public Pagamento pagar(Pedido pedido, MetodoPagamento metodo) {
        Pagamento pagamento = new Pagamento(pedido, metodo);
        pagamento.aprovar(); // simulação: sempre aprova

        // Persiste o recibo antes de avançar o estado do pedido.
        pagamentoRepository.salvar(pagamento);

        pedido.pagar(); // State: Aguardando pagamento -> Pago (dispara os observers)
        return pagamento;
    }

    public void cancelar(Pedido pedido) {
        pedido.cancelar(); // State: Aguardando pagamento -> Cancelado
    }
}
