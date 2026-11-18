package com.example.loja.repository;

import com.example.loja.estado.EstadosPedido;
import com.example.loja.model.Pedido;

import java.util.List;

/**
 * CRUD do Pedido. Os métodos herdados do RepositorioBase já cobrem:
 *   salvar (Create) · buscarPorId e listar (Read) · atualizar (Update) · excluir (Delete).
 * Ao excluir, o cascade leva junto os itens e o histórico do pedido.
 */
public class PedidoRepository extends RepositorioBase<Pedido> {

    public PedidoRepository() {
        super(Pedido.class);
    }

    /** Read: pedidos de um usuário, do mais novo para o mais antigo. */
    public List<Pedido> listarPorUsuario(Integer usuarioId) {
        return consultar(em -> em.createQuery(
                        "select p from Pedido p where p.usuario.id = :id order by p.criadoEm desc", Pedido.class)
                .setParameter("id", usuarioId)
                .getResultList());
    }

    /**
     * Update: grava o estado atual do pedido (coluna status).
     * Chamado pelo PersistenciaPedidoObserver a cada transição do State.
     */
    public void atualizarStatus(Integer pedidoId, String status) {
        executar(em -> {
            Pedido pedido = em.find(Pedido.class, pedidoId);
            if (pedido == null) {
                throw new IllegalArgumentException("Pedido não encontrado: " + pedidoId);
            }
            // A regra da transição já foi validada pelo State no objeto em memória;
            // aqui só copiamos o resultado para a cópia que o EntityManager controla
            if (!status.equals(pedido.getNomeEstado())) {
                pedido.setEstado(EstadosPedido.porNome(status));
            }
            return pedido;
        });
    }
}
