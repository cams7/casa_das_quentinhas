DROP TABLE IF EXISTS acesso;
DROP TABLE IF EXISTS funcionario;
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

CREATE TABLE funcionario (
	id_funcionario INTEGER NOT NULL,	
	funcao SMALLINT NOT NULL,	
	CONSTRAINT funcionario_pk PRIMARY KEY (id_funcionario)
);

ALTER TABLE funcionario ADD CONSTRAINT usuario_funcionario_fk
FOREIGN KEY (id_funcionario)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

CREATE TABLE acesso (
	series VARCHAR(64) NOT NULL,
	email VARCHAR(50) NOT NULL,
	token VARCHAR(64) NOT NULL,
	ultimo_acesso TIMESTAMP NOT NULL,
	CONSTRAINT acesso_pk PRIMARY KEY (series),
	CONSTRAINT acesso_email_uk UNIQUE (email),
	CONSTRAINT acesso_token_uk UNIQUE (token)
);

--password: 12345
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

INSERT INTO funcionario (id_funcionario, funcao) VALUES (1, 0);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (2, 1);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (3, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (4, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (5, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (6, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (7, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (8, 0);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (9, 0);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (10, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (11, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (12, 1);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (13, 1);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (14, 0);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (15, 0);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (16, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (17, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (18, 1);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (19, 1);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (20, 0);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (21, 0);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (22, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (23, 2);
INSERT INTO funcionario (id_funcionario, funcao) VALUES (24, 1);

SELECT u.id_usuario, u.email, f.funcao FROM usuario u INNER JOIN funcionario f ON u.id_usuario=f.id_funcionario; 

