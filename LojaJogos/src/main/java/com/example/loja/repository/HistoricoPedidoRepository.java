package com.example.loja.repository;

import com.example.loja.model.HistoricoPedido;
import com.example.loja.model.Pedido;

import java.util.List;

public class HistoricoPedidoRepository extends RepositorioBase<HistoricoPedido> {

    public HistoricoPedidoRepository() {
        super(HistoricoPedido.class);
    }

    /** Create: registra uma transição de estado de um pedido. */
    public HistoricoPedido registrar(Integer pedidoId, String estadoAnterior, String estadoNovo) {
        return executar(em -> {
            Pedido pedido = em.find(Pedido.class, pedidoId);
            if (pedido == null) {
                throw new IllegalArgumentException("Pedido não encontrado: " + pedidoId);
            }
            HistoricoPedido registro = new HistoricoPedido(pedido, estadoAnterior, estadoNovo);
            em.persist(registro);
            return registro;
        });
    }

    /** Read: histórico de um pedido, em ordem cronológica. */
    public List<HistoricoPedido> listarPorPedido(Integer pedidoId) {
        return consultar(em -> em.createQuery(
                        "select h from HistoricoPedido h where h.pedido.id = :id "
                                + "order by h.registradoEm, h.id",
                        HistoricoPedido.class)
                .setParameter("id", pedidoId)
                .getResultList());
    }
}