package com.example.loja.desconto;

import com.example.loja.model.ItemCarrinho;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class DescontoCupom implements EstrategiaDesconto {

    // Cupons fixos do sistema: código -> percentual
    private static final Map<String, BigDecimal> CUPONS = Map.of(
            "PRIMEIRACOMPRA", new BigDecimal("15"),
            "GAMER20", new BigDecimal("20"),
            "BLACKFRIDAY", new BigDecimal("30")
    );

    private final String codigo;

    public DescontoCupom(String codigo) {
        this.codigo = codigo == null ? "" : codigo.trim().toUpperCase();
    }

    public static boolean cupomValido(String codigo) {
        return codigo != null && CUPONS.containsKey(codigo.trim().toUpperCase());
    }

    public boolean isValido() {
        return CUPONS.containsKey(codigo);
    }

    @Override
    public BigDecimal calcularDesconto(List<ItemCarrinho> itens) {
        if (!isValido()) {
            return BigDecimal.ZERO;
        }
        BigDecimal subtotal = itens.stream()
                .map(ItemCarrinho::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return subtotal.multiply(CUPONS.get(codigo))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return isValido()
                ? "Cupom " + codigo + " (" + CUPONS.get(codigo) + "%)"
                : "Cupom inválido";
    }
}