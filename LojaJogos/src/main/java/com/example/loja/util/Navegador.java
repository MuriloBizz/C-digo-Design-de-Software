package com.example.loja.util;

import com.example.loja.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Troca a tela exibida na janela principal.
 * Uso: Navegador.ir("carrinho");  → abre view/carrinho.fxml
 */
public final class Navegador {

    private static final String PASTA_VIEW = "view/";
    private static final String CSS = "css/estilo.css";

    private static Stage janela;

    private Navegador() {}

    public static void setJanela(Stage stage) {
        janela = stage;
    }

    public static void ir(String tela) {
        String caminho = PASTA_VIEW + tela + ".fxml";
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(caminho));
            Parent raiz = loader.load();

            Scene cena = new Scene(raiz, 820, 560);

            // O CSS é opcional: só é aplicado se o arquivo existir
            URL estilo = App.class.getResource(CSS);
            if (estilo != null) {
                cena.getStylesheets().add(estilo.toExternalForm());
            }

            janela.setScene(cena);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível abrir a tela: " + caminho, e);
        }
    }
}