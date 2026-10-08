package com.example.loja.model;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.estado.PedidoCriado;
import com.example.loja.observer.PedidoObserver;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Pedido {

    private static final AtomicInteger SEQUENCIA = new AtomicInteger(1);

    private final int id;
    private final LocalDateTime criadoEm;
    private final List<ItemCarrinho> itens = new ArrayList<>();
    private final BigDecimal subtotal;
    private final BigDecimal desconto;
    private final BigDecimal total;
    private final String descricaoDesconto;
    private final Usuario usuario;

    private EstadoPedido estado;
    private final List<PedidoObserver> observers = new ArrayList<>();

    public Pedido(Carrinho carrinho, Usuario usuario) {
        if (carrinho == null || carrinho.isVazio()) {
            throw new IllegalArgumentException("Não é possível criar um pedido com o carrinho vazio.");
        }
        this.id = SEQUENCIA.getAndIncrement();
        this.criadoEm = LocalDateTime.now();

        // Cópia dos itens: o pedido não pode mudar se o carrinho for alterado ou limpo depois
        for (ItemCarrinho item : carrinho.getItens()) {
            this.itens.add(new ItemCarrinho(item.getJogo(), item.getQuantidade()));
        }
        this.subtotal = carrinho.getSubtotal();
        this.desconto = carrinho.getDesconto();
        this.total = carrinho.getTotal();
        this.descricaoDesconto = carrinho.getEstrategiaDesconto().getDescricao();

        this.estado = new PedidoCriado();

        if (usuario == null) {
            throw new IllegalArgumentException("O usuário é obrigatório.");
        }
        if (carrinho == null || carrinho.isVazio()) {
            throw new IllegalArgumentException("Não é possível criar um pedido com o carrinho vazio.");
        }
        this.usuario = usuario;
    }

    // ---- Observer (Subject) ----

    public void adicionarObserver(PedidoObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removerObserver(PedidoObserver observer) {
        observers.remove(observer);
    }

    private void notificarObservers(EstadoPedido estadoAnterior) {
        for (PedidoObserver observer : new ArrayList<>(observers)) {
            observer.atualizar(this, estadoAnterior);
        }
    }

    // ---- State (Context): delega ao estado atual ----

    public void confirmar() {
        estado.confirmar(this);
    }

    public void pagar() {
        estado.pagar(this);
    }

    public void concluir() {
        estado.concluir(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    // Chamado pelos estados. É aqui que a mudança de estado dispara o Observer.
    public void setEstado(EstadoPedido novoEstado) {
        EstadoPedido anterior = this.estado;
        this.estado = novoEstado;
        notificarObservers(anterior);
    }

    // ---- Getters ----

    public int getId() { return id; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public List<ItemCarrinho> getItens() { return Collections.unmodifiableList(itens); }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getDesconto() { return desconto; }
    public BigDecimal getTotal() { return total; }
    public String getDescricaoDesconto() { return descricaoDesconto; }
    public EstadoPedido getEstado() { return estado; }
    public String getNomeEstado() { return estado.getNome(); }
    public Usuario getUsuario() { return usuario; }

    @Override
    public String toString() {
        return "Pedido #" + id + " [" + estado.getNome() + "] total R$ " + total;
    }
}