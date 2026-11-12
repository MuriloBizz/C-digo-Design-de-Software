package com.example.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    // null enquanto o item está apenas no carrinho (ainda não virou pedido)
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    protected ItemCarrinho() { }               // exigido pelo JPA

    public ItemCarrinho(Jogo jogo, int quantidade) {
        if (jogo == null) {
            throw new IllegalArgumentException("O jogo é obrigatório.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.jogo = jogo;
        this.quantidade = quantidade;
        this.precoUnitario = jogo.getPreco();   // foto do preço no momento
    }

    public Integer getId() { return id; }
    public Jogo getJogo() { return jogo; }
    public Pedido getPedido() { return pedido; }
    public int getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }

    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    public void aumentarQuantidade(int adicional) {
        if (adicional <= 0) {
            throw new IllegalArgumentException("O valor a adicionar deve ser maior que zero.");
        }
        this.quantidade += adicional;
    }

    public void diminuirQuantidade(int remover) {
        if (remover <= 0 || remover >= quantidade) {
            throw new IllegalArgumentException("Quantidade inválida. Para remover o item, use removerJogo no carrinho.");
        }
        this.quantidade -= remover;
    }

    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return quantidade + "x " + jogo.getTitulo() + " = R$ " + getSubtotal();
    }
}