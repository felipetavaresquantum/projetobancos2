DROP TABLE IF EXISTS fornecedor CASCADE;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS pessoa;
DROP TABLE IF EXISTS endereco;
DROP TABLE IF EXISTS cidade;
DROP TABLE IF EXISTS tipo_pessoa;
DROP TABLE IF EXISTS pessoa_juridica;
DROP TABLE IF EXISTS pessoa_fisica;
DROP VIEW IF EXISTS ViewFornecedorJuridica;
DROP VIEW IF EXISTS ViewFornecedorFisica;
DROP FUNCTION IF EXISTS sp_remover_fornecedor(int);
DROP FUNCTION IF EXISTS sp_historico_insercao(int);
DROP FUNCTION IF EXISTS sp_historico_remocao(int);
DROP TRIGGER IF EXISTS tg_historico_insercao ON historico;
DROP TRIGGER IF EXISTS tg_historico_remocao ON historico;
DROP TABLE IF EXISTS historico;

CREATE TABLE pessoa_fisica
(
	pessoa_fisica_id SERIAL PRIMARY KEY,
	data_nascimento DATE  NOT NULL,
	cpf VARCHAR(11) NOT NULL,
	rg VARCHAR(20) NOT NULL
);

CREATE TABLE pessoa_juridica
(
	pessoa_juridica_id SERIAL PRIMARY KEY,
	data_fundacao DATE NOT NULL,
	cnpj VARCHAR(14) NOT NULL,
	inscricao_estadual VARCHAR(20) NOT NULL,
	inscricao_municipal VARCHAR(30) NOT NULL,
	nome_razao_social VARCHAR(100) NOT NULL,
	nome_fantasia VARCHAR(100) NOT NULL
);

CREATE TABLE tipo_pessoa
(
	tipo_pessoa_id SERIAL PRIMARY KEY,
	tipo VARCHAR(10) NOT NULL
);

CREATE TABLE cidade
(
	cidade_id SERIAL PRIMARY KEY,
	descricao VARCHAR(60) NOT NULL,
	uf CHAR(2) NOT NULL
);

CREATE TABLE endereco
(
	endereco_id SERIAL PRIMARY KEY,
	complemento VARCHAR(100) NOT NULL,
	logradouro VARCHAR(100) NOT NULL,
	cep VARCHAR(10) NOT NULL,
	cidade_id INTEGER 
);

CREATE TABLE pessoa
(
	pessoa_id SERIAL PRIMARY KEY,
	nome VARCHAR(200) NOT NULL,
	tipo_pessoa_id INTEGER,
	endereco_id INTEGER,
	pessoa_fisica_juridica_id INTEGER
);

CREATE TABLE status
(
	status_id SERIAL PRIMARY KEY,
	descricao VARCHAR(20) NOT NULL
);

CREATE TABLE fornecedor
(
	fornecedor_id SERIAL PRIMARY KEY,
	pessoa_id INTEGER,
	status_id INTEGER
);
	
ALTER TABLE endereco ADD CONSTRAINT endereco_fk_cidade FOREIGN KEY (cidade_id) REFERENCES cidade(cidade_id);
ALTER TABLE pessoa ADD CONSTRAINT pessoa_fk_tipo_pessoa FOREIGN KEY (tipo_pessoa_id) REFERENCES tipo_pessoa(tipo_pessoa_id);
ALTER TABLE pessoa ADD CONSTRAINT pessoa_fk_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(endereco_id);
ALTER TABLE fornecedor ADD CONSTRAINT fornecedor_fk_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(pessoa_id);
ALTER TABLE fornecedor ADD CONSTRAINT fornecedor_fk_status FOREIGN KEY (status_id) REFERENCES status(status_id);

CREATE VIEW ViewFornecedorJuridica AS SELECT f.fornecedor_id, p.nome, e.complemento, e.logradouro, e.cep, c.descricao AS cidade, c.uf, s.descricao, t.tipo AS 
status_fornecedor FROM fornecedor AS f INNER JOIN pessoa AS p ON f.pessoa_id = p.pessoa_id INNER JOIN endereco AS e ON p.endereco_id = e.endereco_id INNER JOIN cidade AS c ON 
c.cidade_id = e.cidade_id INNER JOIN status AS s ON s.status_id = f.status_id INNER JOIN tipo_pessoa AS t ON t.tipo_pessoa_id = p.tipo_pessoa_id INNER JOIN pessoa_juridica AS pj ON 
pj.pessoa_juridica_id = p.pessoa_fisica_juridica_id;

CREATE VIEW ViewFornecedorFisica AS SELECT f.fornecedor_id, p.nome, e.complemento, e.logradouro, e.cep, c.descricao AS cidade, c.uf, s.descricao, t.tipo AS 
status_fornecedor FROM fornecedor AS f INNER JOIN pessoa AS p ON f.pessoa_id = p.pessoa_id INNER JOIN endereco AS e ON p.endereco_id = e.endereco_id INNER JOIN cidade AS c ON 
c.cidade_id = e.cidade_id INNER JOIN status AS s ON s.status_id = f.status_id INNER JOIN tipo_pessoa AS t ON t.tipo_pessoa_id = p.tipo_pessoa_id INNER JOIN pessoa_fisica AS pf ON
pf.pessoa_fisica_id = p.pessoa_fisica_juridica_id;

CREATE OR REPLACE FUNCTION sp_remover_fornecedor(id_pessoa INTEGER)
RETURNS void AS $$
DECLARE
	vr_tipo VARCHAR(10);
	vr_pessoa_fisica_juridica_id int;
	vr_pessoa_juridica_id int;
	vr_endereco_id int;
BEGIN
	SELECT tipo, pessoa_fisica_juridica_id, endereco_id INTO vr_tipo, vr_pessoa_fisica_juridica_id, 
  	vr_endereco_id FROM pessoa as p INNER JOIN tipo_pessoa AS tp ON p.tipo_pessoa_id = tp.tipo_pessoa_id WHERE p.pessoa_id = id_pessoa;

	DELETE FROM fornecedor WHERE pessoa_id = id_pessoa;
	DELETE FROM pessoa WHERE pessoa_id = id_pessoa;	
	DELETE FROM endereco WHERE endereco_id = vr_endereco_id;

	CASE UPPER(TRIM(vr_tipo))
	WHEN 'FISICA' THEN
	BEGIN
		DELETE FROM pessoa_fisica WHERE pessoa_fisica_id = vr_pessoa_fisica_juridica_id;
  	END;
	WHEN 'JURIDICA' THEN
	BEGIN
		DELETE FROM pessoa_juridica WHERE pessoa_juridica_id = vr_pessoa_fisica_juridica_id;
	END;
  END CASE;
END;
$$ LANGUAGE 'plpgsql';

CREATE TABLE historico (
  id_historico SERIAL,
  descricao_movimentacao text,
  PRIMARY KEY (id_historico)
);

CREATE OR REPLACE FUNCTION sp_historico_insercao() RETURNS TRIGGER AS
$BODY$
BEGIN
	INSERT INTO historico (descricao_movimentacao) VALUES ('Usuario ' || NEW.nome || ' foi inserido no cadastro.');

	RETURN new;
END;
$BODY$
language plpgsql;

CREATE OR REPLACE FUNCTION sp_historico_remocao() RETURNS TRIGGER AS
$BODY$
BEGIN
	INSERT INTO historico (descricao_movimentacao) VALUES ('Usuario ' || OLD.nome || ' foi removido do cadastro.');

	RETURN new;
END;
$BODY$
language plpgsql;

CREATE TRIGGER tg_historico_insercao
    AFTER INSERT ON pessoa
    FOR EACH ROW
    EXECUTE PROCEDURE sp_historico_insercao();

CREATE TRIGGER tg_historico_remocao
    AFTER DELETE ON pessoa
    FOR EACH ROW
    EXECUTE PROCEDURE sp_historico_remocao();

DELETE FROM tipo_pessoa;
INSERT INTO tipo_pessoa (tipo_pessoa_id, tipo) VALUES ('1','FÍSICA');
INSERT INTO tipo_pessoa (tipo_pessoa_id, tipo) VALUES ('2','JURÍDICA');

DELETE FROM status;
INSERT INTO status (status_id, descricao) VALUES ('1','ATIVO');
INSERT INTO status (status_id, descricao) VALUES ('2','INATIVO');

DELETE FROM cidade;
INSERT INTO cidade (cidade_id, descricao, uf) VALUES ('1', 'NOVA BRASILANDIA D OESTE', 'RO');
INSERT INTO cidade (cidade_id, descricao, uf) VALUES ('2', 'MARECHAL THAUMATURGO', 'AC');
INSERT INTO cidade (cidade_id, descricao, uf) VALUES ('3', 'SAO PAULO DE OLIVENCA', 'AM');
INSERT INTO cidade (cidade_id, descricao, uf) VALUES ('4', 'PALMAS', 'TO');
INSERT INTO cidade (cidade_id, descricao, uf) VALUES ('5', 'IPAMERI', 'GO');