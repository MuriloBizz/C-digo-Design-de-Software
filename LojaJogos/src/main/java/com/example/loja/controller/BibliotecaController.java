package com.example.loja.controller;

import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import com.example.loja.util.Sessao.ItemTela;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class BibliotecaController {

    @FXML private ListView<ItemTela> listaJogos;
    @FXML private Label vazio;

    @FXML
    private void initialize() {
        // TODO: trocar por usuario.getBiblioteca().getJogos()
        listaJogos.setItems(Sessao.biblioteca);
        vazio.setVisible(Sessao.biblioteca.isEmpty());
    }

    @FXML private void voltar() { Navegador.ir("catalogo"); }
}