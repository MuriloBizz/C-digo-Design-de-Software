package com.example.loja.repository;

import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.Jogo;
import com.example.loja.model.Pedido;

import java.util.List;

public class ItemCarrinhoRepository extends RepositorioBase<ItemCarrinho> {

    public ItemCarrinhoRepository() {
        super(ItemCarrinho.class);
    }

    /** Create: cria um item de um pedido a partir dos ids. */
    public ItemCarrinho criar(Integer pedidoId, Integer jogoId, int quantidade) {
        return executar(em -> {
            Pedido pedido = em.find(Pedido.class, pedidoId);
            Jogo jogo = em.find(Jogo.class, jogoId);
            if (pedido == null || jogo == null) {
                throw new IllegalArgumentException("Pedido ou jogo não encontrado.");
            }
            ItemCarrinho item = new ItemCarrinho(jogo, quantidade);
            item.setPedido(pedido);
            em.persist(item);
            return item;
        });
    }

    /** Read: itens de um pedido. */
    public List<ItemCarrinho> listarPorPedido(Integer pedidoId) {
        return consultar(em -> em.createQuery(
                        "select i from ItemCarrinho i where i.pedido.id = :id order by i.id",
                        ItemCarrinho.class)
                .setParameter("id", pedidoId)
                .getResultList());
    }

    /** Update: altera a quantidade de um item. */
    public ItemCarrinho atualizarQuantidade(Integer itemId, int quantidade) {
        return executar(em -> {
            ItemCarrinho item = em.find(ItemCarrinho.class, itemId);
            if (item == null) {
                throw new IllegalArgumentException("Item não encontrado: " + itemId);
            }
            item.setQuantidade(quantidade);   // objeto gerenciado: o commit grava a mudança
            return item;
        });
    }
}