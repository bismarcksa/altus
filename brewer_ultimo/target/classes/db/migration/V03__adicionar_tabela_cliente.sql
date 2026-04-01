CREATE TABLE cliente (
    codigo BIGINT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    tipo_pessoa VARCHAR(15) NOT NULL,
    cpf_cnpj VARCHAR(30) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(50) NOT NULL,
    logradouro VARCHAR(50),
    numero VARCHAR(15),
    complemento VARCHAR(100),
    cep VARCHAR(15),
    codigo_cidade BIGINT,
    FOREIGN KEY (codigo_cidade) REFERENCES cidade(codigo)
);

CREATE SEQUENCE sequence_cliente INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;
