package com.example.loja.catalogo;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogoJogos extends Application {

    private BorderPane root;
    private List<Game> gameDatabase;
    private FlowPane catalogPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeData();

        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #1e1e24;");

        // Topo com o Logo
        Label logo = new Label("FORJA");
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        logo.setTextFill(Color.web("#ff4500"));
        HBox header = new HBox(logo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(header);

        showCatalog();

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Forja - Catálogo de Jogos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // RF07: O sistema deve exibir um catálogo de jogos.
    private void showCatalog() {
        VBox catalogView = new VBox(15);

        // RF08: O usuário deve poder pesquisar jogos por nome.
        TextField searchBar = new TextField();
        searchBar.setPromptText("Pesquisar jogos por nome...");
        searchBar.setStyle("-fx-font-size: 16px; -fx-background-color: #2b2b36; -fx-text-fill: white;");
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterGames(newValue));

        catalogPane = new FlowPane();
        catalogPane.setHgap(15);
        catalogPane.setVgap(15);

        filterGames(""); // Carrega todos os jogos inicialmente

        ScrollPane scrollPane = new ScrollPane(catalogPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #1e1e24; -fx-border-color: #1e1e24;");

        catalogView.getChildren().addAll(searchBar, scrollPane);
        root.setCenter(catalogView);
    }

    private void filterGames(String query) {
        catalogPane.getChildren().clear();
        List<Game> filtered = gameDatabase.stream()
                .filter(g -> g.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        for (Game game : filtered) {
            catalogPane.getChildren().add(createGameCard(game));
        }
    }

    private VBox createGameCard(Game game) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #2b2b36; -fx-background-radius: 8; -fx-cursor: hand;");
        card.setPrefWidth(200);

        Label title = new Label(game.getTitle());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setTextFill(Color.WHITE);

        Label price = new Label(String.format("R$ %.2f", game.getPrice()));
        price.setTextFill(Color.LIGHTGREEN);

        card.getChildren().addAll(title, price);

        // RF11: Informar se o jogo está em promoção
        if (game.isOnSale()) {
            Label saleLabel = new Label("PROMOÇÃO");
            saleLabel.setStyle("-fx-background-color: #ff4500; -fx-text-fill: white; -fx-padding: 3 6; -fx-background-radius: 4;");
            card.getChildren().add(saleLabel);
            price.setText(String.format("De: R$ %.2f\nPor: R$ %.2f", game.getPrice(), game.getSalePrice()));
        }

        // RF09: Cada jogo deve possuir uma página própria.
        card.setOnMouseClicked(e -> showGamePage(game));

        return card;
    }

    // RF10: Página do jogo com título, descrição, vídeos, preço e requisitos.
    private void showGamePage(Game game) {
        VBox page = new VBox(20);
        page.setPadding(new Insets(20));
        page.setStyle("-fx-background-color: #2b2b36; -fx-background-radius: 8;");

        Button btnBack = new Button("← Voltar ao Catálogo");
        btnBack.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-cursor: hand;");
        btnBack.setOnAction(e -> showCatalog());

        Label title = new Label(game.getTitle());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.WHITE);

        Text description = new Text(game.getDescription());
        description.setFill(Color.LIGHTGRAY);
        description.setWrappingWidth(600);

        // RF11: Preço e Promoção na página
        HBox priceBox = new HBox(10);
        priceBox.setAlignment(Pos.CENTER_LEFT);
        Label price = new Label();
        price.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        if (game.isOnSale()) {
            price.setText(String.format("PROMOÇÃO: R$ %.2f (Era R$ %.2f)", game.getSalePrice(), game.getPrice()));
            price.setTextFill(Color.web("#ff4500"));
        } else {
            price.setText(String.format("R$ %.2f", game.getPrice()));
            price.setTextFill(Color.LIGHTGREEN);
        }
        priceBox.getChildren().add(price);


        Label reqTitle = new Label("Requisitos do Sistema:");
        reqTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        reqTitle.setTextFill(Color.WHITE);

        Text requirements = new Text(game.getSystemRequirements());
        requirements.setFill(Color.LIGHTGRAY);

        page.getChildren().addAll(btnBack, title, priceBox, description, reqTitle, requirements);

        ScrollPane scrollPage = new ScrollPane(page);
        scrollPage.setFitToWidth(true);
        scrollPage.setStyle("-fx-background: #1e1e24; -fx-border-color: #1e1e24;");

        root.setCenter(scrollPage);
    }

    private void initializeData() {
        gameDatabase = new ArrayList<>();
        gameDatabase.add(new Game(
                "Aura das Lâminas",
                "Um RPG de ação em mundo aberto onde você forja seu próprio destino.",
                199.90,
                "SO: Windows 10\nProcessador: Intel i5\nMemória: 8GB RAM\nPlaca de Vídeo: GTX 1060",
                false, 0
        ));
        gameDatabase.add(new Game(
                "Forja Galáctica",
                "Explore o universo, construa naves e combata impérios alienígenas.",
                149.50,
                "SO: Windows 10/11\nProcessador: Intel i7\nMemória: 16GB RAM\nPlaca de Vídeo: RTX 3060",
                true, 89.90
        ));
    }

    // Classe de Modelo (Model)
    class Game {
        private String title, description, systemRequirements;
        private double price, salePrice;
        private boolean isOnSale;

        public Game(String title, String description, double price, String systemRequirements, boolean isOnSale, double salePrice) {
            this.title = title;
            this.description = description;
            this.price = price;
            this.systemRequirements = systemRequirements;
            this.isOnSale = isOnSale;
            this.salePrice = salePrice;
        }

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public String getSystemRequirements() { return systemRequirements; }
        public boolean isOnSale() { return isOnSale; }
        public double getSalePrice() { return salePrice; }
    }
}