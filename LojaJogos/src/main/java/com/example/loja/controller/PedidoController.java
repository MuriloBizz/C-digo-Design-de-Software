package com.example.loja.controller;

import com.example.loja.estado.PedidoPago;
import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.Pedido;
import com.example.loja.service.PedidoService;
import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class PedidoController {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @FXML private Label lblEstado;
    @FXML private Label lblNumero;
    @FXML private Label lblData;
    @FXML private Label lblItens;
    @FXML private Label lblSubtotal;
    @FXML private Label lblDesconto;
    @FXML private Label lblTotal;
    @FXML private Label lblMensagem;
    @FXML private ListView<String> listaHistorico;
    @FXML private Button btnConcluir;

    private final PedidoService pedidoService = new PedidoService();
    private Pedido pedido;

    @FXML
    private void initialize() {
        pedido = Sessao.getPedidoAtual();

        if (pedido == null) {
            lblNumero.setText("Nenhum pedido em andamento.");
            btnConcluir.setDisable(true);
            return;
        }

        lblNumero.setText("Pedido #" + pedido.getId());
        lblData.setText("Criado em " + pedido.getCriadoEm().format(FORMATO_DATA));
        lblItens.setText(pedido.getItens().stream()
                .map(ItemCarrinho::toString)
                .collect(Collectors.joining("\n")));
        lblSubtotal.setText(String.format("Subtotal: R$ %.2f", pedido.getSubtotal()));
        lblDesconto.setText(String.format("Desconto (%s): - R$ %.2f",
                pedido.getDescricaoDesconto(), pedido.getDesconto()));
        lblTotal.setText(String.format("Total: R$ %.2f", pedido.getTotal()));

        atualizarTela();
    }

    @FXML
    private void concluir() {
        try {
            pedidoService.concluir(pedido);   // State: Pago -> Concluído
            lblMensagem.setText("");
        } catch (IllegalStateException e) {
            lblMensagem.setText(e.getMessage());
        }
        atualizarTela();
    }

    private void atualizarTela() {
        lblEstado.setText("Estado: " + pedido.getNomeEstado());
        btnConcluir.setDisable(!(pedido.getEstado() instanceof PedidoPago));

        // Observer: o histórico é alimentado pelo LogObserver a cada transição
        listaHistorico.setItems(FXCollections.observableArrayList(
                Sessao.getLogPedido().getHistorico()));
    }

    @FXML
    private void irParaBiblioteca() {
        Navegador.ir("biblioteca");
    }

    @FXML
    private void voltarALoja() {
        Navegador.ir("catalogo");
    }
}