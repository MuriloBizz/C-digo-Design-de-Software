package com.example.loja.controller;

import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PedidoController {

    @FXML private Label estado;
    @FXML private ListView<String> listaNotificacoes;

    @FXML
    private void initialize() {
        // TODO: trocar por pedido.getEstado()  (State)
        estado.setText("Estado do pedido: " + Sessao.estadoPedido);

        // Mensagens geradas pelos observers quando o pedido é pago (Observer)
        listaNotificacoes.setItems(Sessao.notificacoes);
    }

    @FXML private void abrirBiblioteca() { Navegador.ir("biblioteca"); }
    @FXML private void voltar()          { Navegador.ir("catalogo"); }
}