package com.example.loja.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class Navegador {

    private static Stage stage;
    private static Scene scene;

    private Navegador() { }

    public static void iniciar(Stage janela, double largura, double altura) {
        stage = janela;
        scene = new Scene(new javafx.scene.layout.StackPane(), largura, altura);
        stage.setScene(scene);
    }

    public static void ir(String nomeTela) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Navegador.class.getResource("/com/example/loja/view/" + nomeTela + ".fxml"));
            Parent raiz = loader.load();
            scene.setRoot(raiz);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível abrir a tela: " + nomeTela, e);
        }
    }
}