package com.example.loja.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Guarda os dados compartilhados entre as telas enquanto o app está aberto.
 *
 * TEMPORÁRIO: usa dados de exemplo (ItemTela) para as telas funcionarem
 * antes do model ficar pronto. Depois, troque por Jogo, Carrinho e Pedido.
 */
public final class Sessao {

    /** Item simples só para exibição. Substituir por Jogo quando o model estiver pronto. */
    public record ItemTela(String titulo, double precoBase, double precoFinal) {

        public boolean temDesconto() {
            return precoFinal < precoBase;
        }

        @Override
        public String toString() {
            if (temDesconto()) {
                return String.format("%s   R$ %.2f  →  R$ %.2f", titulo, precoBase, precoFinal);
            }
            return String.format("%s   R$ %.2f", titulo, precoFinal);
        }
    }

    public static final ObservableList<ItemTela> catalogo = FXCollections.observableArrayList(
            new ItemTela("Hollow Knight", 50.00, 35.00),
            new ItemTela("Celeste", 40.00, 40.00),
            new ItemTela("Stardew Valley", 25.00, 25.00),
            new ItemTela("Dead Cells", 60.00, 45.00)
    );

    public static final ObservableList<ItemTela> carrinho = FXCollections.observableArrayList();
    public static final ObservableList<ItemTela> biblioteca = FXCollections.observableArrayList();
    public static final ObservableList<String> notificacoes = FXCollections.observableArrayList();

    public static String estadoPedido = "Nenhum pedido";

    private Sessao() {}

    public static double totalCarrinho() {
        return carrinho.stream().mapToDouble(ItemTela::precoFinal).sum();
    }
}