package com.example.loja.desconto;

import com.example.loja.model.ItemCarrinho;

import java.math.BigDecimal;
import java.util.List;

public class SemDesconto implements EstrategiaDesconto {

    @Override
    public BigDecimal calcularDesconto(List<ItemCarrinho> itens) {
        return BigDecimal.ZERO;
    }

    @Override
    public String getDescricao() {
        return "Sem desconto";
    }
}