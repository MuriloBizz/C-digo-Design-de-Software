package com.example.loja.controller;

import com.example.loja.catalogo.CatalogoJogos;
import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.Jogo;
import com.example.loja.util.Navegador;
import com.example.loja.util.Sessao;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CatalogoController {

    private static final String COR_FUNDO = "#1e1e24";

    @FXML private StackPane areaCentral;
    @FXML private Button btnCarrinho;

    private final CatalogoJogos catalogo = CatalogoJogos.getInstancia(); // Singleton
    private FlowPane painelJogos;

    @FXML
    private void initialize() {
        mostrarCatalogo();
        atualizarContador();
    }

    // RF07 + RF08: catálogo com pesquisa por nome
    private void mostrarCatalogo() {
        TextField busca = new TextField();
        busca.setPromptText("Pesquisar jogos por nome...");
        busca.setStyle("-fx-font-size: 16px; -fx-background-color: #2b2b36; -fx-text-fill: white;");
        busca.textProperty().addListener((obs, antigo, novo) -> filtrar(novo));

        painelJogos = new FlowPane(15, 15);

        ScrollPane rolagem = new ScrollPane(painelJogos);
        rolagem.setFitToWidth(true);
        rolagem.setStyle("-fx-background: " + COR_FUNDO + "; -fx-border-color: " + COR_FUNDO + ";");

        VBox tela = new VBox(15, busca, rolagem);
        areaCentral.getChildren().setAll(tela);

        filtrar("");
    }

    private void filtrar(String consulta) {
        painelJogos.getChildren().clear();
        for (Jogo jogo : catalogo.buscarPorTitulo(consulta)) {
            painelJogos.getChildren().add(criarCard(jogo));
        }
    }

    private VBox criarCard(Jogo jogo) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(200);
        card.setStyle("-fx-background-color: #2b2b36; -fx-background-radius: 8; -fx-cursor: hand;");

        Label titulo = new Label(jogo.getTitulo());
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titulo.setTextFill(Color.WHITE);
        titulo.setWrapText(true);

        Label preco = new Label(String.format("R$ %.2f", jogo.getPreco()));
        preco.setTextFill(Color.LIGHTGREEN);

        card.getChildren().addAll(titulo, preco);

        // RF11: informar promoção
        if (jogo.isEmPromocao()) {
            Label selo = new Label("PROMOÇÃO");
            selo.setStyle("-fx-background-color: #ff4500; -fx-text-fill: white; "
                    + "-fx-padding: 3 6; -fx-background-radius: 4;");
            card.getChildren().add(selo);
            preco.setText(String.format("De: R$ %.2f\nPor: R$ %.2f",
                    jogo.getPrecoOriginal(), jogo.getPreco()));
        }

        card.getChildren().add(criarBotaoAdicionar(jogo));

        // RF09: página própria do jogo
        card.setOnMouseClicked(e -> mostrarPaginaJogo(jogo));
        return card;
    }

    private Button criarBotaoAdicionar(Jogo jogo) {
        Button botao = new Button();
        botao.setStyle("-fx-background-color: #ff4500; -fx-text-fill: white; -fx-cursor: hand;");

        if (Sessao.getUsuario().getBiblioteca().possui(jogo)) {
            botao.setText("Na biblioteca");
            botao.setDisable(true);
        } else {
            botao.setText("Adicionar ao carrinho");
            botao.setOnAction(e -> {
                Sessao.getCarrinho().adicionarJogo(jogo);
                atualizarContador();
            });
        }
        return botao;
    }

    // RF10: página do jogo (título, preço, descrição e requisitos)
    private void mostrarPaginaJogo(Jogo jogo) {
        VBox pagina = new VBox(20);
        pagina.setPadding(new Insets(20));
        pagina.setStyle("-fx-background-color: #2b2b36; -fx-background-radius: 8;");

        Button voltar = new Button("← Voltar ao Catálogo");
        voltar.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-cursor: hand;");
        voltar.setOnAction(e -> mostrarCatalogo());

        Label titulo = new Label(jogo.getTitulo());
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setTextFill(Color.WHITE);

        Label preco = new Label();
        preco.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        if (jogo.isEmPromocao()) {
            preco.setText(String.format("PROMOÇÃO: R$ %.2f (Era R$ %.2f)",
                    jogo.getPreco(), jogo.getPrecoOriginal()));
            preco.setTextFill(Color.web("#ff4500"));
        } else {
            preco.setText(String.format("R$ %.2f", jogo.getPreco()));
            preco.setTextFill(Color.LIGHTGREEN);
        }

        Text descricao = new Text(jogo.getDescricao());
        descricao.setFill(Color.LIGHTGRAY);
        descricao.setWrappingWidth(600);

        Label tituloReq = new Label("Requisitos do Sistema:");
        tituloReq.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        tituloReq.setTextFill(Color.WHITE);

        Text requisitos = new Text(jogo.getRequisitosSistema());
        requisitos.setFill(Color.LIGHTGRAY);

        HBox acoes = new HBox(10, criarBotaoAdicionar(jogo));
        acoes.setAlignment(Pos.CENTER_LEFT);

        pagina.getChildren().addAll(voltar, titulo, preco, descricao, tituloReq, requisitos, acoes);

        ScrollPane rolagem = new ScrollPane(pagina);
        rolagem.setFitToWidth(true);
        rolagem.setStyle("-fx-background: " + COR_FUNDO + "; -fx-border-color: " + COR_FUNDO + ";");
        areaCentral.getChildren().setAll(rolagem);
    }

    private void atualizarContador() {
        int total = Sessao.getCarrinho().getItens().stream()
                .mapToInt(ItemCarrinho::getQuantidade)
                .sum();
        btnCarrinho.setText("Carrinho (" + total + ")");
    }

    @FXML
    private void irParaCarrinho() {
        Navegador.ir("carrinho");
    }

    @FXML
    private void irParaBiblioteca() {
        Navegador.ir("biblioteca");
    }
}