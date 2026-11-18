CREATE DATABASE IF NOT EXISTS forja
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
  
  USE forja;

-- 1. Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 2. Tabela de Biblioteca (Relação 1:1 ou 1:N com Usuario)
-- Cada usuário possui uma biblioteca pessoal de jogos
CREATE TABLE IF NOT EXISTS biblioteca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    CONSTRAINT fk_biblioteca_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 3. Tabela de Jogos
CREATE TABLE IF NOT EXISTS jogo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    preco_original DECIMAL(10, 2) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    preco_promocional DECIMAL(10, 2),
    capa VARCHAR(255),
    descricao TEXT,
    requisitos_sistema TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 4. Tabela de Associação N:N entre Biblioteca e Jogo (Jogos adquiridos na biblioteca)
CREATE TABLE IF NOT EXISTS biblioteca_jogo (
    biblioteca_id INT NOT NULL,
    jogo_id INT NOT NULL,
    PRIMARY KEY (biblioteca_id, jogo_id),
    CONSTRAINT fk_bibliotecajogo_biblioteca FOREIGN KEY (biblioteca_id) REFERENCES biblioteca(id) ON DELETE CASCADE,
    CONSTRAINT fk_bibliotecajogo_jogo FOREIGN KEY (jogo_id) REFERENCES jogo(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 5. Tabela de Pedidos
CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    criado_em DATETIME NOT NULL,
    usuario_id INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    desconto DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    descricao_desconto VARCHAR(100),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 6. Tabela de Itens do Carrinho / Pedido
CREATE TABLE IF NOT EXISTS item_carrinho (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    jogo_id INT NOT NULL,
    quantidade INT NOT NULL,
    CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_jogo FOREIGN KEY (jogo_id) REFERENCES jogo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 7. Tabela de Pagamentos
CREATE TABLE IF NOT EXISTS pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(10, 2) NOT NULL,
    metodo ENUM('PIX', 'CARTAO', 'BOLETO') NOT NULL,
    status ENUM('PENDENTE', 'APROVADO', 'RECUSADO') NOT NULL,
    criado_em DATETIME NOT NULL,
    processadoem DATETIME,
    pedido_id INT NOT NULL,
    CONSTRAINT fk_pagamento_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 8. Tabela de Histórico de Pedidos (LogObserver / C15)
CREATE TABLE IF NOT EXISTS historico_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    transicao VARCHAR(255) NOT NULL,
    registrado_em DATETIME NOT NULL,
    CONSTRAINT fk_historico_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;