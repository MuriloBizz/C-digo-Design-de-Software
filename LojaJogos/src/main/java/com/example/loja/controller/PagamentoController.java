package com.example.loja.controller;

import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class PagamentoController {

    @FXML private ChoiceBox<String> metodoPagamento;
    @FXML private Label total;

    @FXML
    private void initialize() {
        metodoPagamento.getItems().addAll("Cartão de crédito", "Pix", "Boleto");
        metodoPagamento.getSelectionModel().selectFirst();
        total.setText(String.format("Total a pagar: R$ %.2f", Sessao.totalCarrinho()));
    }

    @FXML
    private void pagar() {
        // TODO: trocar por checkoutFacade.finalizarCompra(usuario, carrinho)  (Facade)
        // O Facade cria o pedido, paga, muda o estado (State) e avisa os observers (Observer).
        Sessao.estadoPedido = "Pago";
        Sessao.biblioteca.addAll(Sessao.carrinho);
        Sessao.notificacoes.add("[E-mail] Compra confirmada: " + Sessao.carrinho.size() + " jogo(s).");
        Sessao.notificacoes.add("[Biblioteca] Jogos liberados.");
        Sessao.notificacoes.add("[Log] Venda registrada.");
        Sessao.carrinho.clear();

        Navegador.ir("pedido");
    }

    @FXML
    private void simularRecusa() {
        // Mostra o State indo para "Cancelado". O carrinho é mantido.
        Sessao.estadoPedido = "Cancelado (pagamento recusado)";
        Navegador.ir("pedido");
    }

    @FXML private void voltar() { Navegador.ir("carrinho"); }
}