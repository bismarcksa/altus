CREATE TABLE venda (
    codigo BIGINT PRIMARY KEY,
    data_criacao TIMESTAMP NOT NULL,
    valor_frete DECIMAL(10,2),
    valor_desconto DECIMAL(10,2),
    valor_total DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    observacao VARCHAR(200),
    data_hora_entrega TIMESTAMP,
    codigo_cliente BIGINT NOT NULL,
    codigo_usuario BIGINT NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
);

CREATE SEQUENCE sequence_venda INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE item_venda (
    codigo BIGINT PRIMARY KEY,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    codigo_cerveja BIGINT NOT NULL,
    codigo_venda BIGINT NOT NULL,
    FOREIGN KEY (codigo_cerveja) REFERENCES cerveja(codigo),
    FOREIGN KEY (codigo_venda) REFERENCES venda(codigo)
);

CREATE SEQUENCE sequence_item_venda INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

