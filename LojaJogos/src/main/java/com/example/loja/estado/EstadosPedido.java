package com.example.loja.estado;

/**
 * Converte o texto gravado na coluna "status" de volta para o objeto de estado.
 *
 * O padrão State usa objetos (PedidoPago, PedidoCancelado...), mas o banco só guarda texto.
 * Na gravação usamos EstadoPedido.getNome(); na leitura, este método.
 */
public final class EstadosPedido {

    private EstadosPedido() { }

    public static EstadoPedido porNome(String nome) {
        if (nome == null) {
            return new PedidoCriado();
        }
        return switch (nome) {
            case "Criado" -> new PedidoCriado();
            case "Aguardando pagamento" -> new PedidoAguardandoPagamento();
            case "Pago" -> new PedidoPago();
            case "Concluído" -> new PedidoConcluido();
            case "Cancelado" -> new PedidoCancelado();
            default -> throw new IllegalArgumentException("Estado de pedido desconhecido: " + nome);
        };
    }
}
