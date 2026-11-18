package com.example.loja.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa o pagamento realizado para um pedido.
 * Os dados ficam persistidos para que o recibo possa ser consultado depois.
 */
@Entity
@Table(name = "pagamento")
public class Pagamento {

    public enum Status { PENDENTE, APROVADO, RECUSADO }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo", nullable = false, length = 30)
    private MetodoPagamento metodo;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.PENDENTE;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "processado_em")
    private LocalDateTime processadoEm;

    // Construtor exigido pelo JPA
    protected Pagamento() { }

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
        this.status = Status.PENDENTE;
        this.criadoEm = LocalDateTime.now();
    }

    public void aprovar() {
        validarPendente();
        this.status = Status.APROVADO;
        this.processadoEm = LocalDateTime.now();
    }

    public void recusar() {
        validarPendente();
        this.status = Status.RECUSADO;
        this.processadoEm = LocalDateTime.now();
    }

    private void validarPendente() {
        if (status != Status.PENDENTE) {
            throw new IllegalStateException("O pagamento já foi processado: " + status);
        }
    }

    public Integer getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public MetodoPagamento getMetodo() { return metodo; }
    public BigDecimal getValor() { return valor; }
    public Status getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getProcessadoEm() { return processadoEm; }

    @Override
    public String toString() {
        return "Pagamento #" + id + " - " + metodo
                + " - R$ " + valor + " [" + status + "]";
    }
}
