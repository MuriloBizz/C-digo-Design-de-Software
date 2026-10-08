package com.example.loja.desconto;

import com.example.loja.model.ItemCarrinho;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DescontoPercentual implements EstrategiaDesconto {

    private final BigDecimal percentual; // ex.: 10 = 10%

    public DescontoPercentual(BigDecimal percentual) {
        if (percentual == null
                || percentual.signum() < 0
                || percentual.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("O percentual deve estar entre 0 e 100.");
        }
        this.percentual = percentual;
    }

    @Override
    public BigDecimal calcularDesconto(List<ItemCarrinho> itens) {
        BigDecimal subtotal = itens.stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return subtotal.multiply(percentual)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Desconto de " + percentual.stripTrailingZeros().toPlainString() + "%";
    }
}