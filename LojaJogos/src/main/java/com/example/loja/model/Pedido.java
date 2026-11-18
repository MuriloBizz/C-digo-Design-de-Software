package com.example.loja.model;

import com.example.loja.estado.EstadoPedido;
import com.example.loja.estado.EstadosPedido;
import com.example.loja.estado.PedidoCriado;
import com.example.loja.observer.PedidoObserver;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Pedido de compra, persistido na tabela "pedido".
 *
 * O que vai para o banco: id, usuário, data, subtotal, desconto, total,
 * descrição do desconto e o NOME do estado (coluna status).
 * O objeto de estado (padrão State) e os observers NÃO vão para o banco (@Transient).
 */
@Entity
@Table(name = "pedido")
public class Pedido {

    // O banco gera o número (auto-incremento). Substitui o antigo AtomicInteger.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    // N:1 — um usuário pode ter vários pedidos. A FK usuario_id fica nesta tabela.
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // 1:N — os itens nascem e morrem com o pedido (composição).
    // O lado dono é o ItemCarrinho (campo "pedido", FK pedido_id na tabela item_pedido).
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER)
    @OrderBy("id")
    private List<ItemCarrinho> itens = new ArrayList<>();

    // 1:N — ao excluir o pedido, o histórico dele é excluído junto
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    private List<HistoricoPedido> historico = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal desconto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "descricao_desconto", length = 100)
    private String descricaoDesconto;

    // O banco só guarda texto: "Criado", "Aguardando pagamento", "Pago"...
    @Column(nullable = false, length = 40)
    private String status;

    // O objeto do padrão State é recriado a partir de "status" no @PostLoad
    @Transient
    private EstadoPedido estado;

    @Transient
    private final List<PedidoObserver> observers = new ArrayList<>();

    protected Pedido() { }                     // exigido pelo JPA

    public Pedido(Usuario usuario, Carrinho carrinho) {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário é obrigatório.");
        }
        if (carrinho == null || carrinho.isVazio()) {
            throw new IllegalArgumentException("Não é possível criar um pedido com o carrinho vazio.");
        }
        this.criadoEm = LocalDateTime.now();
        this.usuario = usuario;

        // Cópia dos itens: o pedido não pode mudar se o carrinho for alterado ou limpo depois.
        // Cada cópia aponta para este pedido (é isso que preenche a FK pedido_id).
        for (ItemCarrinho item : carrinho.getItens()) {
            ItemCarrinho copia = new ItemCarrinho(item.getJogo(), item.getQuantidade());
            copia.setPedido(this);
            this.itens.add(copia);
        }
        this.subtotal = carrinho.getSubtotal();
        this.desconto = carrinho.getDesconto();
        this.total = carrinho.getTotal();
        this.descricaoDesconto = carrinho.getEstrategiaDesconto().getDescricao();

        this.estado = new PedidoCriado();
        this.status = estado.getNome();
    }

    /** Executado pelo EclipseLink logo depois de carregar o pedido do banco. */
    @PostLoad
    private void restaurarEstado() {
        this.estado = EstadosPedido.porNome(status);
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

    // Chamado pelos estados. Atualiza também a coluna status e dispara o Observer.
    public void setEstado(EstadoPedido novoEstado) {
        EstadoPedido anterior = this.estado;
        this.estado = novoEstado;
        this.status = novoEstado.getNome();
        notificarObservers(anterior);
    }

    // ---- Getters ----

    public Integer getId() { return id; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public List<ItemCarrinho> getItens() { return Collections.unmodifiableList(itens); }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getDesconto() { return desconto; }
    public BigDecimal getTotal() { return total; }
    public String getDescricaoDesconto() { return descricaoDesconto; }
    public EstadoPedido getEstado() { return estado; }
    public String getNomeEstado() { return status; }
    public Usuario getUsuario() { return usuario; }

    // Dois pedidos são iguais se tiverem o mesmo id do banco
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido outro)) return false;
        return id != null && id.equals(outro.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return "Pedido #" + id + " [" + status + "] total R$ " + total;
    }
}
