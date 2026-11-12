package com.example.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "historico_pedido")
public class HistoricoPedido {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "estado_anterior", length = 40)
    private String estadoAnterior;

    @Column(name = "estado_novo", nullable = false, length = 40)
    private String estadoNovo;

    @Column(name = "registrado_em", nullable = false)
    private LocalDateTime registradoEm;

    protected HistoricoPedido() { }            // exigido pelo JPA

    public HistoricoPedido(Pedido pedido, String estadoAnterior, String estadoNovo) {
        if (pedido == null) {
            throw new IllegalArgumentException("O pedido é obrigatório.");
        }
        if (estadoNovo == null || estadoNovo.isBlank()) {
            throw new IllegalArgumentException("O novo estado é obrigatório.");
        }
        this.pedido = pedido;
        this.estadoAnterior = estadoAnterior;
        this.estadoNovo = estadoNovo;
        this.registradoEm = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public String getEstadoNovo() { return estadoNovo; }
    public LocalDateTime getRegistradoEm() { return registradoEm; }

    public void setEstadoAnterior(String estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    public void setEstadoNovo(String estadoNovo) { this.estadoNovo = estadoNovo; }

    /** Linha de texto exibida na tela de pedido. */
    public String getDescricao() {
        return registradoEm.format(FORMATO) + " | Pedido #" + pedido.getId()
                + ": " + estadoAnterior + " -> " + estadoNovo;
    }

    @Override
    public String toString() { return getDescricao(); }
}