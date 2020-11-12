CREATE SCHEMA IF NOT EXISTS Warehouse;
SET search_path to warehouse;

CREATE TABLE IF NOT EXISTS armazem(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(50) UNIQUE,
	localizacao VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS fornecedor(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(50),
	id_armazem INTEGER NOT NULL,
	FOREIGN KEY (id_armazem) REFERENCES armazem(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS maquina(
	numero_serie VARCHAR(50) PRIMARY KEY,
	nome VARCHAR(50),
	avariada BOOLEAN,
	id_armazem INTEGER NOT NULL,
	descricao_avaria VARCHAR(200),
	FOREIGN KEY (id_armazem) REFERENCES armazem(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS funcionario(
	nif VARCHAR(50) PRIMARY KEY,
	nome VARCHAR(50),
	idade INTEGER,
	funcao VARCHAR(50),
	salario Double precision,
	id_armazem INTEGER NOT NULL,
	FOREIGN KEY (id_armazem) REFERENCES armazem(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS login(
	username VARCHAR(50) PRIMARY KEY,
	password VARCHAR(200),
	data_criacao TIMESTAMP,
	admin BOOLEAN,
	nif_funcionario VARCHAR(50) NOT NULL,
	email VARCHAR(200),
	FOREIGN KEY (nif_funcionario) REFERENCES funcionario(nif) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS categoria_produto(
	nome  VARCHAR(50) PRIMARY KEY,
	id_armazem INTEGER NOT NULL,
	FOREIGN KEY (id_armazem) REFERENCES armazem(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sub_categoria(
	sub_categoria VARCHAR(50) PRIMARY KEY,
	categoria VARCHAR(50) NOT NULL,
	FOREIGN KEY (categoria) REFERENCES categoria_produto(nome) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lote(
	numero_lote VARCHAR(50) PRIMARY KEY,
	origem VARCHAR(50),
	data_de_chegada TIMESTAMP,
	sub_categoria VARCHAR(50) NOT NULL,
	nome VARCHAR(50),
	FOREIGN KEY (sub_categoria) REFERENCES sub_categoria(sub_categoria) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS produto(
	sku VARCHAR(50) PRIMARY KEY,
	data_saida TIMESTAMP,
	destino VARCHAR(50),
	com_defeito BOOLEAN,
	num_lote VARCHAR(50) NOT NULL,
	vendido BOOLEAN,
	FOREIGN KEY (num_lote) REFERENCES lote(numero_lote) ON DELETE CASCADE
);

--logs das ações dos users--
CREATE TABLE IF NOT EXISTS logs(
	id SERIAL PRIMARY KEY,
	data TIMESTAMP,
	username VARCHAR(50),
	admin BOOLEAN,
	acao_min VARCHAR(200),
	acao_compl VARCHAR(400),
	ip VARCHAR(50),
	id_armazem INTEGER NOT NULL,
    FOREIGN KEY (id_armazem) REFERENCES armazem(id) ON DELETE CASCADE
);