DROP TABLE IF EXISTS acesso;
DROP TABLE IF EXISTS usuario_autorizacao;
DROP TABLE IF EXISTS autorizacao;
DROP TABLE IF EXISTS usuario;

DROP SEQUENCE IF EXISTS id_usuario_seq;
CREATE SEQUENCE id_usuario_seq;

CREATE TABLE usuario (
	id_usuario INTEGER NOT NULL DEFAULT nextval('id_usuario_seq'),
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(100) NOT NULL,
	nome VARCHAR(30) NOT NULL,
	sobrenome VARCHAR(30) NOT NULL,	
    CONSTRAINT usuario_pk PRIMARY KEY (id_usuario),
	CONSTRAINT usuario_email_uk UNIQUE (email)
);

ALTER SEQUENCE id_usuario_seq OWNED BY usuario.id_usuario;

DROP SEQUENCE IF EXISTS id_autorizacao_seq;
CREATE SEQUENCE id_autorizacao_seq;

DROP TYPE IF EXISTS autorizacao_papel;
CREATE TYPE autorizacao_papel AS ENUM ('ADMIN', 'DBA', 'USER');

CREATE TABLE autorizacao (
	id_autorizacao SMALLINT NOT NULL DEFAULT nextval('id_autorizacao_seq'),
	papel autorizacao_papel NOT NULL,
    CONSTRAINT autorizacao_pk PRIMARY KEY (id_autorizacao),
	CONSTRAINT autorizacao_papel_uk UNIQUE (papel)
);

ALTER SEQUENCE id_autorizacao_seq OWNED BY autorizacao.id_autorizacao;

CREATE TABLE usuario_autorizacao (
	id_usuario INTEGER NOT NULL,
	id_autorizacao SMALLINT NOT NULL,
    CONSTRAINT usuario_autorizacao_pk PRIMARY KEY (id_usuario, id_autorizacao)
);

ALTER TABLE usuario_autorizacao ADD CONSTRAINT usuario_autorizacao_usuario_fk
FOREIGN KEY (id_usuario)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE usuario_autorizacao ADD CONSTRAINT usuario_autorizacao_autorizacao_fk
FOREIGN KEY (id_autorizacao)
REFERENCES autorizacao (id_autorizacao)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

CREATE TABLE acesso (
	series VARCHAR(64) NOT NULL,
    login VARCHAR(64) NOT NULL,    
    token VARCHAR(64) NOT NULL,
    ultimo_acesso TIMESTAMP NOT NULL,
    CONSTRAINT acesso_pk PRIMARY KEY (series)
);

INSERT INTO autorizacao(id_autorizacao, papel) VALUES (nextval('id_autorizacao_seq'), 'USER');  
INSERT INTO autorizacao(id_autorizacao, papel) VALUES (nextval('id_autorizacao_seq'), 'ADMIN');  
INSERT INTO autorizacao(id_autorizacao, papel) VALUES (nextval('id_autorizacao_seq'), 'DBA');

--Username: cesar, password: 12345
INSERT INTO usuario(id_usuario, email, senha, nome, sobrenome) VALUES (nextval('id_usuario_seq'),'ceanma@gmail.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 'César','Magalhães');

INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (1, 2);

SELECT u.id_usuario, a.id_autorizacao FROM usuario u INNER JOIN usuario_autorizacao ua ON u.id_usuario=ua.id_usuario INNER JOIN autorizacao a ON ua.id_autorizacao=a.id_autorizacao WHERE u.email='ceanma@gmail.com' AND a.papel='ADMIN'; 

