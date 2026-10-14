package com.example.loja.controller;

import com.example.loja.desconto.DescontoCombo;
import com.example.loja.desconto.DescontoCupom;
import com.example.loja.desconto.DescontoPercentual;
import com.example.loja.desconto.EstrategiaDesconto;
import com.example.loja.model.Carrinho;
import com.example.loja.model.ItemCarrinho;
import com.example.loja.service.CarrinhoService;
import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CarrinhoController {

    private enum OpcaoDesconto {
        NENHUM("Sem desconto"),
        PERCENTUAL("Desconto de 10%"),
        CUPOM("Cupom"),
        COMBO("Combo: leve 3, pague 2");

        private final String rotulo;

        OpcaoDesconto(String rotulo) { this.rotulo = rotulo; }

        @Override
        public String toString() { return rotulo; }
    }

    @FXML private TableView<ItemCarrinho> tabelaItens;
    @FXML private TableColumn<ItemCarrinho, String> colJogo;
    @FXML private TableColumn<ItemCarrinho, String> colPreco;
    @FXML private TableColumn<ItemCarrinho, String> colQuantidade;
    @FXML private TableColumn<ItemCarrinho, String> colSubtotal;
    @FXML private TableColumn<ItemCarrinho, Void> colAcao;
    @FXML private Label lblVazio;
    @FXML private ComboBox<OpcaoDesconto> cbDesconto;
    @FXML private VBox boxCupom;
    @FXML private TextField txtCupom;
    @FXML private Label lblCupomMensagem;
    @FXML private Label lblDescricaoDesconto;
    @FXML private Label lblSubtotal;
    @FXML private Label lblDesconto;
    @FXML private Label lblTotal;
    @FXML private Button btnFinalizar;

    private final Carrinho carrinho = Sessao.getCarrinho();
    private final CarrinhoService carrinhoService = new CarrinhoService();

    @FXML
    private void initialize() {
        configurarTabela();
        configurarDesconto();
        atualizarTela();
    }

    private void configurarTabela() {
        colJogo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getJogo().getTitulo()));
        colPreco.setCellValueFactory(c -> new SimpleStringProperty(
                String.format("R$ %.2f", c.getValue().getJogo().getPreco())));
        colQuantidade.setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getQuantidade())));
        colSubtotal.setCellValueFactory(c -> new SimpleStringProperty(
                String.format("R$ %.2f", c.getValue().getSubtotal())));

        colAcao.setCellFactory(coluna -> new TableCell<>() {
            private final Button remover = new Button("Remover");

            {
                remover.setStyle("-fx-background-color: #b3261e; -fx-text-fill: white; -fx-cursor: hand;");
                remover.setOnAction(e -> {
                    ItemCarrinho item = getTableView().getItems().get(getIndex());
                    carrinhoService.removerJogo(carrinho, item.getJogo());
                    atualizarTela();
                });
            }

            @Override
            protected void updateItem(Void vazio, boolean empty) {
                super.updateItem(vazio, empty);
                setGraphic(empty ? null : remover);
            }
        });
    }

    // ---- Strategy: o ComboBox escolhe, o service instancia a estratégia ----

    private void configurarDesconto() {
        cbDesconto.setItems(FXCollections.observableArrayList(OpcaoDesconto.values()));
        cbDesconto.setValue(opcaoAtual());
        mostrarCampoCupom(cbDesconto.getValue() == OpcaoDesconto.CUPOM);

        cbDesconto.setOnAction(e -> {
            OpcaoDesconto opcao = cbDesconto.getValue();
            mostrarCampoCupom(opcao == OpcaoDesconto.CUPOM);
            lblCupomMensagem.setText("");

            switch (opcao) {
                case PERCENTUAL -> carrinhoService.aplicarDescontoPercentual(carrinho);
                case COMBO -> carrinhoService.aplicarDescontoCombo(carrinho);
                default -> carrinhoService.removerDesconto(carrinho); // NENHUM e CUPOM (até aplicar o código)
            }
            atualizarTela();
        });
    }

    private OpcaoDesconto opcaoAtual() {
        EstrategiaDesconto atual = carrinhoService.getEstrategiaAtual(carrinho);
        if (atual instanceof DescontoPercentual) return OpcaoDesconto.PERCENTUAL;
        if (atual instanceof DescontoCupom) return OpcaoDesconto.CUPOM;
        if (atual instanceof DescontoCombo) return OpcaoDesconto.COMBO;
        return OpcaoDesconto.NENHUM;
    }

    private void mostrarCampoCupom(boolean mostrar) {
        boxCupom.setVisible(mostrar);
        boxCupom.setManaged(mostrar);
    }

    @FXML
    private void aplicarCupom() {
        boolean aplicado = carrinhoService.aplicarCupom(carrinho, txtCupom.getText());
        lblCupomMensagem.setText(aplicado ? "Cupom aplicado!" : "Cupom inválido.");
        atualizarTela();
    }


    private void atualizarTela() {
        tabelaItens.setItems(FXCollections.observableArrayList(carrinho.getItens()));

        boolean vazio = carrinho.isVazio();
        lblVazio.setVisible(vazio);
        lblVazio.setManaged(vazio);
        btnFinalizar.setDisable(vazio);

        lblSubtotal.setText(String.format("Subtotal: R$ %.2f", carrinho.getSubtotal()));
        lblDesconto.setText(String.format("Desconto: - R$ %.2f", carrinho.getDesconto()));
        lblTotal.setText(String.format("Total: R$ %.2f", carrinho.getTotal()));
        lblDescricaoDesconto.setText("Regra aplicada: " + carrinho.getEstrategiaDesconto().getDescricao());
    }

    @FXML
    private void voltarAoCatalogo() {
        Navegador.ir("catalogo");
    }

    @FXML
    private void finalizarCompra() {
        Navegador.ir("pagamento");
    }
}