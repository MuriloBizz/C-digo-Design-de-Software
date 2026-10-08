package com.example.loja;

import com.example.loja.catalogo.CatalogoJogos;
import com.example.loja.util.Navegador;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        CatalogoJogos.getInstancia(); // carrega o catálogo ao iniciar (eager)
        stage.setTitle("Forja - Loja de Jogos");
        Navegador.iniciar(stage, 1000, 700);
        Navegador.ir("catalogo");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}