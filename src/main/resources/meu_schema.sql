CREATE TABLE cliente(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

CREATE TABLE produto(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(100),
    preco_unitario NUMERIC(20,2)
);

CREATE TABLE pedido(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT REFERENCES cliente (id),
    data_pedido TIMESTAMP,
    total NUMERIC(20,2)
);

CREATE TABLE item_pedido(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT REFERENCES pedido (id),
    produto_id BIGINT REFERENCES produto (id),
    quantidade BIGINT
);