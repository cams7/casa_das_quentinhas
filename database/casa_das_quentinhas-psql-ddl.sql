DROP TABLE IF EXISTS acesso;
DROP TABLE IF EXISTS usuario_autorizacao;
DROP TABLE IF EXISTS autorizacao;
DROP TABLE IF EXISTS usuario;

DROP SEQUENCE IF EXISTS usuario_id_seq;
CREATE SEQUENCE usuario_id_seq;

CREATE TABLE usuario (
	id_usuario INTEGER NOT NULL DEFAULT nextval('usuario_id_seq'),
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(100) NOT NULL,		
    	CONSTRAINT usuario_pk PRIMARY KEY (id_usuario),
	CONSTRAINT usuario_email_uk UNIQUE (email)
);

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id_usuario;

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

CREATE TABLE public.acesso (
	series VARCHAR(64) NOT NULL,
	email VARCHAR(50) NOT NULL,
	token VARCHAR(64) NOT NULL,
	ultimo_acesso TIMESTAMP NOT NULL,
	CONSTRAINT acesso_pk PRIMARY KEY (series),
	CONSTRAINT acesso_email_uk UNIQUE (email),
	CONSTRAINT acesso_token_uk UNIQUE (token)
);

INSERT INTO autorizacao(id_autorizacao, papel) VALUES (nextval('id_autorizacao_seq'), 'USER');  
INSERT INTO autorizacao(id_autorizacao, papel) VALUES (nextval('id_autorizacao_seq'), 'ADMIN');  
INSERT INTO autorizacao(id_autorizacao, papel) VALUES (nextval('id_autorizacao_seq'), 'DBA');

--Username: cesar, password: 12345
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'ceanma@gmail.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'leone@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'leandro@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'daniel@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'cintia@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'violeta@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'ariana@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'bianca@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'micaela@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'henrique@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'jeronimo@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'natal@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'antoniela@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'sabrina@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'carolina@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'nadia@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'evandro@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'elias@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'maximo@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'carlos@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'rafael@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'maximiano@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'julia@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');
INSERT INTO usuario(id_usuario, email, senha) VALUES (nextval('usuario_id_seq'),'guilherme@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC');

INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (1, 2);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (2, 1);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (3, 1);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (4, 3);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (5, 3);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (6, 2);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (7, 2);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (8, 1);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (9, 1);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (10, 3);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (11, 3);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (12, 2);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (13, 2);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (14, 1);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (15, 1);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (16, 3);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (17, 3);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (18, 2);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (19, 2);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (20, 1);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (21, 1);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (22, 3);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (23, 3);
INSERT INTO usuario_autorizacao (id_usuario, id_autorizacao) VALUES (24, 2);

SELECT u.id_usuario, u.email, a.papel FROM usuario u INNER JOIN usuario_autorizacao ua ON u.id_usuario=ua.id_usuario INNER JOIN autorizacao a ON ua.id_autorizacao=a.id_autorizacao; 

