package com.example.loja.desconto;

import com.example.loja.model.ItemCarrinho;

import java.math.BigDecimal;
import java.util.List;

public interface EstrategiaDesconto {

    BigDecimal calcularDesconto(List<ItemCarrinho> itens);

    String getDescricao();
}