module com.example.loja {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.loja to javafx.fxml;
    opens com.example.loja.controller to javafx.fxml;
    opens com.example.loja.model to javafx.fxml;

    exports com.example.loja;
    exports com.example.loja.model;
    exports com.example.loja.catalogo;
    exports com.example.loja.desconto;
    exports com.example.loja.estado;
    exports com.example.loja.observer;
    exports com.example.loja.service;
    exports com.example.loja.util;
}