package com.example.loja.controller;

import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import com.example.loja.util.Sessao.ItemTela;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class CatalogoController {

    @FXML private ListView<ItemTela> listaJogos;
    @FXML private Label mensagem;

    @FXML
    private void initialize() {
        // TODO: trocar por CatalogoJogos.getInstance().listar()  (Singleton)
        listaJogos.setItems(Sessao.catalogo);
    }

    @FXML
    private void adicionarAoCarrinho() {
        ItemTela selecionado = listaJogos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mensagem.setText("Selecione um jogo primeiro.");
        } else if (Sessao.biblioteca.contains(selecionado)) {
            mensagem.setText("Você já possui " + selecionado.titulo() + ".");
        } else if (Sessao.carrinho.contains(selecionado)) {
            mensagem.setText(selecionado.titulo() + " já está no carrinho.");
        } else {
            Sessao.carrinho.add(selecionado);
            mensagem.setText(selecionado.titulo() + " adicionado ao carrinho.");
        }
    }

    @FXML private void abrirCarrinho()   { Navegador.ir("carrinho"); }
    @FXML private void abrirBiblioteca() { Navegador.ir("biblioteca"); }
}