DROP TABLE IF EXISTS acesso;
DROP TABLE IF EXISTS usuario_funcionario;
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

DROP SEQUENCE IF EXISTS funcionario_id_seq;
CREATE SEQUENCE funcionario_id_seq;

CREATE TABLE funcionario (
	id_funcionario SMALLINT NOT NULL DEFAULT nextval('funcionario_id_seq'),
	funcao SMALLINT NOT NULL,
    CONSTRAINT funcionario_pk PRIMARY KEY (id_funcionario),
	CONSTRAINT funcionario_funcao_uk UNIQUE (funcao)
);

ALTER SEQUENCE funcionario_id_seq OWNED BY funcionario.id_funcionario;

CREATE TABLE usuario_funcionario (
	id_usuario INTEGER NOT NULL,
	id_funcionario SMALLINT NOT NULL,
    CONSTRAINT usuario_funcionario_pk PRIMARY KEY (id_usuario, id_funcionario)
);

ALTER TABLE usuario_funcionario ADD CONSTRAINT usuario_funcionario_usuario_fk
FOREIGN KEY (id_usuario)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE usuario_funcionario ADD CONSTRAINT usuario_funcionario_funcionario_fk
FOREIGN KEY (id_funcionario)
REFERENCES funcionario (id_funcionario)
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

  
INSERT INTO funcionario(id_funcionario, funcao) VALUES (nextval('funcionario_id_seq'), 0);  
INSERT INTO funcionario(id_funcionario, funcao) VALUES (nextval('funcionario_id_seq'), 1);
INSERT INTO funcionario(id_funcionario, funcao) VALUES (nextval('funcionario_id_seq'), 2);

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

INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (1, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (2, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (3, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (4, 3);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (5, 3);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (6, 2);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (7, 2);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (8, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (9, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (10, 3);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (11, 3);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (12, 2);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (13, 2);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (14, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (15, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (16, 3);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (17, 3);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (18, 2);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (19, 2);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (20, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (21, 1);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (22, 3);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (23, 3);
INSERT INTO usuario_funcionario (id_usuario, id_funcionario) VALUES (24, 2);

SELECT u.id_usuario, u.email, a.funcao FROM usuario u INNER JOIN usuario_funcionario ua ON u.id_usuario=ua.id_usuario INNER JOIN funcionario a ON ua.id_funcionario=a.id_funcionario; 

