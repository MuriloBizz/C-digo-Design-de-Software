package com.example.loja.observer;

import com.example.loja.model.ItemCarrinho;
import com.example.loja.model.cruds.Bibliotecacrud;
import com.example.loja.estado.EstadoPedido;
import com.example.loja.estado.PedidoPago;
import com.example.loja.model.Biblioteca;
import com.example.loja.model.ItemCarrinho; // Ajustado caso sua classe se chame ItemPedido conforme o persistence.xml
import com.example.loja.model.Pedido;

public class BibliotecaObserver implements PedidoObserver {

    private final Bibliotecacrud bibliotecacrud = new Bibliotecacrud();

    @Override
    public void atualizar(Pedido pedido, EstadoPedido estadoAnterior) {
        if (pedido.getEstado() instanceof PedidoPago) {
            // Percorre os itens do pedido
            for (ItemCarrinho item : pedido.getItens()) {
                // Cria a nova entidade Biblioteca ligando o usuário e o jogo comprado
                Biblioteca novaBiblioteca = new Biblioteca(pedido.getUsuario(), item.getJogo());

                // Salva o registro no banco de dados através do DAO
                bibliotecacrud.salvar(novaBiblioteca);
            }
        }
    }
}