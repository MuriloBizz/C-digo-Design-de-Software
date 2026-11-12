package com.example.loja.observer;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.model.HistoricoPedido;
import com.example.loja.model.Pedido;
import com.example.loja.repository.HistoricoPedidoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogObserver implements PedidoObserver {

    private final List<String> historico = new ArrayList<>();
    private final HistoricoPedidoRepository repositorio = new HistoricoPedidoRepository();

    @Override
    public void atualizar(Pedido pedido, EstadoPedido estadoAnterior) {
        String linha = LocalDateTime.now() + " | Pedido #" + pedido.getId()
                + ": " + estadoAnterior.getNome() + " -> " + pedido.getNomeEstado();
        historico.add(linha);
        System.out.println("[LOG] " + linha);

        try {
            repositorio.registrar(pedido.getId(), estadoAnterior.getNome(), pedido.getNomeEstado());
        } catch (RuntimeException e) {
            // Uma falha de banco não pode interromper os outros observers (ex.: BibliotecaObserver)
            System.err.println("[LOG] Não foi possível gravar o histórico: " + e.getMessage());
        }
    }

    public List<String> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}