package com.example.loja.controller;

import com.example.loja.estado.PedidoAguardandoPagamento;
import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.MetodoPagamento;
import com.example.loja.model.Pedido;
import com.example.loja.service.PagamentoService;
import com.example.loja.service.PedidoService;
import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.stream.Collectors;

public class PagamentoController {

    @FXML private Label lblEstado;
    @FXML private Label lblPedido;
    @FXML private Label lblItens;
    @FXML private Label lblSubtotal;
    @FXML private Label lblDesconto;
    @FXML private Label lblTotal;
    @FXML private ComboBox<MetodoPagamento> cbMetodo;
    @FXML private Label lblMensagem;
    @FXML private Button btnPagar;
    @FXML private Button btnCancelar;
    @FXML private Button btnVoltar;

    private final PedidoService pedidoService = new PedidoService();
    private final PagamentoService pagamentoService = new PagamentoService();

    private Pedido pedido;

    @FXML
    private void initialize() {
        pedido = obterPedido();

        cbMetodo.setItems(FXCollections.observableArrayList(MetodoPagamento.values()));
        cbMetodo.getSelectionModel().selectFirst();

        lblPedido.setText("Pedido #" + pedido.getId());
        lblItens.setText(pedido.getItens().stream()
                .map(ItemCarrinho::toString)
                .collect(Collectors.joining("\n")));
        lblSubtotal.setText(String.format("Subtotal: R$ %.2f", pedido.getSubtotal()));
        lblDesconto.setText(String.format("Desconto (%s): - R$ %.2f",
                pedido.getDescricaoDesconto(), pedido.getDesconto()));
        lblTotal.setText(String.format("Total: R$ %.2f", pedido.getTotal()));

        atualizarEstado();
    }

    /**
     * Reaproveita o pedido da sessão se ele ainda estiver aguardando pagamento
     * (ex.: usuário voltou para esta tela). Caso contrário, cria um novo.
     */
    private Pedido obterPedido() {
        Pedido atual = Sessao.getPedidoAtual();
        if (atual != null && atual.getEstado() instanceof PedidoAguardandoPagamento) {
            return atual;
        }
        Sessao.novoLog();
        Pedido novo = pedidoService.criarPedido(
                Sessao.getUsuario(), Sessao.getCarrinho(), Sessao.getLogPedido());
        Sessao.setPedidoAtual(novo);
        return novo;
    }

    @FXML
    private void pagar() {
        MetodoPagamento metodo = cbMetodo.getValue();
        if (metodo == null) {
            lblMensagem.setText("Selecione um método de pagamento.");
            return;
        }
        try {
            pagamentoService.pagar(pedido, metodo);   // State + Observer
            Sessao.novoCarrinho();                    // carrinho esvazia após a compra
            Navegador.ir("pedido");
        } catch (IllegalStateException e) {
            lblMensagem.setText(e.getMessage());
            atualizarEstado();
        }
    }

    @FXML
    private void cancelar() {
        try {
            pagamentoService.cancelar(pedido);        // State: -> Cancelado
            Sessao.setPedidoAtual(null);
            Navegador.ir("carrinho");
        } catch (IllegalStateException e) {
            lblMensagem.setText(e.getMessage());
            atualizarEstado();
        }
    }

    private void atualizarEstado() {
        lblEstado.setText("Estado: " + pedido.getNomeEstado());
        boolean aguardando = pedido.getEstado() instanceof PedidoAguardandoPagamento;
        btnPagar.setDisable(!aguardando);
        btnCancelar.setDisable(!aguardando);
    }
    @FXML
    private void voltarAoCatalogo() {
        try {
            if (pedido.getEstado() instanceof PedidoAguardandoPagamento) {
                pagamentoService.cancelar(pedido);   // State: -> Cancelado (dispara os observers)
            }
            Sessao.setPedidoAtual(null);
            Navegador.ir("catalogo");
        } catch (IllegalStateException e) {
            lblMensagem.setText(e.getMessage());
            atualizarEstado();
        }
    }
}