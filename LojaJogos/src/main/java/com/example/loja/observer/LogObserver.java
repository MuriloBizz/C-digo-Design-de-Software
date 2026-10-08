package com.example.loja.observer;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.model.Pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogObserver implements PedidoObserver {

    private final List<String> historico = new ArrayList<>();

    @Override
    public void atualizar(Pedido pedido, EstadoPedido estadoAnterior) {
        String linha = LocalDateTime.now() + " | Pedido #" + pedido.getId()
                + ": " + estadoAnterior.getNome() + " -> " + pedido.getNomeEstado();
        historico.add(linha);
        System.out.println("[LOG] " + linha);
    }

    public List<String> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}