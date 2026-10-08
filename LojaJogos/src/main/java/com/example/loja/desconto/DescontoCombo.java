package com.example.loja.desconto;

import com.example.loja.model.ItemCarrinho;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DescontoCombo implements EstrategiaDesconto {

    private static final int TAMANHO_GRUPO = 3;

    @Override
    public BigDecimal calcularDesconto(List<ItemCarrinho> itens) {
        List<BigDecimal> precos = new ArrayList<>();
        for (ItemCarrinho item : itens) {
            for (int i = 0; i < item.getQuantidade(); i++) {
                precos.add(item.getJogo().getPreco());
            }
        }
        precos.sort(Comparator.reverseOrder());

        BigDecimal desconto = BigDecimal.ZERO;
        // posições 2, 5, 8... (o 3º de cada grupo, que é o mais barato do grupo)
        for (int i = TAMANHO_GRUPO - 1; i < precos.size(); i += TAMANHO_GRUPO) {
            desconto = desconto.add(precos.get(i));
        }
        return desconto;
    }

    @Override
    public String getDescricao() {
        return "Combo: leve 3, pague 2";
    }
}