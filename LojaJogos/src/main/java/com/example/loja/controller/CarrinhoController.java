package com.example.loja.controller;

import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import com.example.loja.util.Sessao.ItemTela;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class CarrinhoController {

    @FXML private ListView<ItemTela> listaItens;
    @FXML private Label total;
    @FXML private Label mensagem;
    @FXML private Button botaoPagamento;

    @FXML
    private void initialize() {
        listaItens.setItems(Sessao.carrinho);
        atualizarTotal();
    }

    @FXML
    private void removerItem() {
        ItemTela selecionado = listaItens.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mensagem.setText("Selecione um item para remover.");
            return;
        }
        Sessao.carrinho.remove(selecionado);
        mensagem.setText(selecionado.titulo() + " removido.");
        atualizarTotal();
    }

    private void atualizarTotal() {
        // TODO: trocar por carrinho.calcularTotal()  (usa os preços da Strategy)
        total.setText(String.format("Total: R$ %.2f", Sessao.totalCarrinho()));
        botaoPagamento.setDisable(Sessao.carrinho.isEmpty());
    }

    @FXML private void irParaPagamento() { Navegador.ir("pagamento"); }
    @FXML private void voltar()          { Navegador.ir("catalogo"); }
}