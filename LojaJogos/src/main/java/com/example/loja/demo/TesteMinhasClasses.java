package com.example.loja.demo;

import com.example.loja.model.Carrinho;
import com.example.loja.model.HistoricoPedido;
import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.Jogo;
import com.example.loja.model.Pedido;
import com.example.loja.model.Usuario;
import com.example.loja.repository.HistoricoPedidoRepository;
import com.example.loja.repository.ItemCarrinhoRepository;
import com.example.loja.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class TesteMinhasClasses {
    public static void main(String[] args) {
        // ---- Preparação: um jogo e um pedido (com 1 item) ----
        EntityManager em = JPAUtil.criarEntityManager();
        em.getTransaction().begin();
        Jogo jogo = new Jogo("Jogo de teste", new BigDecimal("50.00"),
                "Teste", "teste.png", "Descrição", "Requisitos", null);
        em.persist(jogo);

        Carrinho carrinho = new Carrinho();
        carrinho.adicionarJogo(jogo);
        Pedido pedido = new Pedido(new Usuario("Teste", "teste@email.com"), carrinho);
        em.persist(pedido);                       // a cascata grava o item
        em.getTransaction().commit();
        Integer pedidoId = pedido.getId();
        Integer jogoId = jogo.getId();
        em.close();

        ItemCarrinhoRepository itens = new ItemCarrinhoRepository();
        HistoricoPedidoRepository historico = new HistoricoPedidoRepository();

        // ---- CRUD de ItemCarrinho ----
        System.out.println("== ItemCarrinho ==");
        ItemCarrinho item = itens.criar(pedidoId, jogoId, 2);                       // Create
        System.out.println("Criado: " + item);
        System.out.println("Itens do pedido: " + itens.listarPorPedido(pedidoId));  // Read
        itens.atualizarQuantidade(item.getId(), 5);                                 // Update
        System.out.println("Após update: " + itens.buscarPorId(item.getId()).orElseThrow());
        System.out.println("Excluído? " + itens.excluir(item.getId()));             // Delete
        System.out.println("Itens após excluir: " + itens.listarPorPedido(pedidoId));

        // ---- CRUD de HistoricoPedido ----
        System.out.println("== HistoricoPedido ==");
        HistoricoPedido h = historico.registrar(pedidoId, "Criado", "Aguardando pagamento"); // Create
        System.out.println("Criado: " + h);
        System.out.println("Histórico do pedido: " + historico.listarPorPedido(pedidoId));    // Read
        h.setEstadoNovo("Pago");
        historico.atualizar(h);                                                             // Update
        System.out.println("Após update: " + historico.buscarPorId(h.getId()).orElseThrow());
        System.out.println("Excluído? " + historico.excluir(h.getId()));                    // Delete
        System.out.println("Histórico após excluir: " + historico.listarPorPedido(pedidoId));

        JPAUtil.fechar();
    }
}