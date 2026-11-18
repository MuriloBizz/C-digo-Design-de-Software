package com.example.loja.repository;

import com.example.loja.model.Pagamento;

import java.util.List;

/**
 * Acesso aos pagamentos armazenados no banco de dados.
 * O CRUD básico (salvar, buscarPorId, listar, atualizar, excluir) vem do RepositorioBase.
 */
public class PagamentoRepository extends RepositorioBase<Pagamento> {

    public PagamentoRepository() {
        super(Pagamento.class);
    }

    /** Lista os pagamentos vinculados a um pedido. */
    public List<Pagamento> listarPorPedido(Integer pedidoId) {
        return consultar(em -> em.createQuery(
                        "select p from Pagamento p where p.pedido.id = :pedidoId order by p.criadoEm desc",
                        Pagamento.class)
                .setParameter("pedidoId", pedidoId)
                .getResultList());
    }

    /**
     * Exclui todos os pagamentos de um pedido.
     * Deve ser chamado ANTES de excluir o pedido: enquanto existir um pagamento
     * apontando para ele (FK pedido_id), o banco não deixa o pedido ser apagado.
     *
     * @return quantos pagamentos foram excluídos
     */
    public int excluirPorPedido(Integer pedidoId) {
        return executar(em -> em.createQuery(
                        "delete from Pagamento p where p.pedido.id = :pedidoId")
                .setParameter("pedidoId", pedidoId)
                .executeUpdate());
    }
}
