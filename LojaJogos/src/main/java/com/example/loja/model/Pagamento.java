package com.example.loja.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pagamento {

    public enum Status { PENDENTE, APROVADO, RECUSADO }

    private final Pedido pedido;
    private final MetodoPagamento metodo;
    private final BigDecimal valor;
    private final LocalDateTime criadoEm;

    private Status status = Status.PENDENTE;
    private LocalDateTime processadoEm;

    public Pagamento(Pedido pedido, MetodoPagamento metodo) {
        if (pedido == null) {
            throw new IllegalArgumentException("O pedido é obrigatório.");
        }
        if (metodo == null) {
            throw new IllegalArgumentException("Selecione um método de pagamento.");
        }
        this.pedido = pedido;
        this.metodo = metodo;
        this.valor = pedido.getTotal();
        this.criadoEm = LocalDateTime.now();
    }

    public void aprovar() {
        if (status != Status.PENDENTE) {
            throw new IllegalStateException("O pagamento já foi processado: " + status);
        }
        this.status = Status.APROVADO;
        this.processadoEm = LocalDateTime.now();
    }

    public void recusar() {
        if (status != Status.PENDENTE) {
            throw new IllegalStateException("O pagamento já foi processado: " + status);
        }
        this.status = Status.RECUSADO;
        this.processadoEm = LocalDateTime.now();
    }

    public Pedido getPedido() { return pedido; }
    public MetodoPagamento getMetodo() { return metodo; }
    public BigDecimal getValor() { return valor; }
    public Status getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getProcessadoEm() { return processadoEm; }

    @Override
    public String toString() {
        return "Pagamento " + metodo + " R$ " + valor + " [" + status + "]";
    }
}