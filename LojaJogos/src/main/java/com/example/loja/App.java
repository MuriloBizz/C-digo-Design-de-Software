package com.example.loja;

import com.example.loja.util.Navegador;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Forja");
        Navegador.setJanela(stage);
        Navegador.ir("catalogo");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}