package com.example.loja.controller;

import com.example.loja.model.Jogo;
import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class BibliotecaController {

    @FXML private FlowPane painelJogos;
    @FXML private Label lblVazio;
    @FXML private Label lblQuantidade;

    @FXML
    private void initialize() {
        // Observer (resultado): estes jogos foram adicionados pelo BibliotecaObserver
        List<Jogo> jogos = Sessao.getUsuario().getBiblioteca().getJogos();

        for (Jogo jogo : jogos) {
            painelJogos.getChildren().add(criarCard(jogo));
        }

        boolean vazia = jogos.isEmpty();
        lblVazio.setVisible(vazia);
        lblVazio.setManaged(vazia);
        lblQuantidade.setText(jogos.size() + (jogos.size() == 1 ? " jogo" : " jogos"));
    }

    private VBox criarCard(Jogo jogo) {
        VBox card = new VBox(8);
        card.setPrefWidth(200);
        card.setStyle("-fx-background-color: #2b2b36; -fx-background-radius: 8; -fx-padding: 15;");

        Label titulo = new Label(jogo.getTitulo());
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titulo.setTextFill(Color.WHITE);
        titulo.setWrapText(true);

        Label genero = new Label(jogo.getGenero());
        genero.setTextFill(Color.LIGHTGRAY);

        Label status = new Label("✔ Na biblioteca");
        status.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; "
                + "-fx-padding: 3 6; -fx-background-radius: 4;");

        card.getChildren().addAll(titulo, genero, status);
        return card;
    }

    @FXML
    private void voltarALoja() {
        Navegador.ir("catalogo");
    }
}