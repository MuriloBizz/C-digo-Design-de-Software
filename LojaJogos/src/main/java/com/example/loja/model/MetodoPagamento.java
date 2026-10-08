package com.example.loja.model;

public enum MetodoPagamento {

    PIX("PIX"),
    CARTAO("Cartão de crédito"),
    BOLETO("Boleto bancário");

    private final String descricao;

    MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}