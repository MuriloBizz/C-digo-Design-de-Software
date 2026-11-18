package com.example.loja.observer;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.estado.PedidoPago;
import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.Pedido;
import com.example.loja.service.BibliotecaService;

public class BibliotecaObserver implements PedidoObserver {

    private final BibliotecaService biblioteca = new BibliotecaService();

    @Override
    public void atualizar(Pedido pedido, EstadoPedido estadoAnterior) {
        if (pedido.getEstado() instanceof PedidoPago) {
            for (ItemCarrinho item : pedido.getItens()) {
                try {
                    biblioteca.adicionar(pedido.getUsuario().getId(), item.getJogo());
                } catch (RuntimeException e) {
                    // Uma falha aqui não pode impedir os outros observers
                    System.err.println("[BIBLIOTECA] Falha ao gravar "
                            + item.getJogo().getTitulo() + ": " + e.getMessage());
                }
            }
        }
    }
}