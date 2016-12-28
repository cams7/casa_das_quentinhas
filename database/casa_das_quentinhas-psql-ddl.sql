DROP TABLE IF EXISTS public.acesso;
DROP TABLE IF EXISTS public.funcionario;
DROP TABLE IF EXISTS public.empresa;
DROP TABLE IF EXISTS public.usuario;

DROP SEQUENCE IF EXISTS public.usuario_id_seq;
CREATE SEQUENCE public.usuario_id_seq;

CREATE TABLE public.usuario (
	id_usuario INTEGER NOT NULL DEFAULT nextval('public.usuario_id_seq'),
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    tipo_usuario SMALLINT NOT NULL,
    CONSTRAINT usuario_pk PRIMARY KEY (id_usuario),
	CONSTRAINT usuario_email_uk UNIQUE (email)
);

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id_usuario;

DROP SEQUENCE IF EXISTS public.empresa_id_seq;
CREATE SEQUENCE public.empresa_id_seq;

CREATE TABLE public.empresa (
    id_empresa INTEGER NOT NULL DEFAULT nextval('public.empresa_id_seq'),
    razao_social VARCHAR(60) NOT NULL,
	cnpj VARCHAR(14) NOT NULL,
    CONSTRAINT empresa_pk PRIMARY KEY (id_empresa),
	CONSTRAINT empresa_cnpj_uk UNIQUE (cnpj)
);

ALTER SEQUENCE public.empresa_id_seq OWNED BY public.empresa.id_empresa;

CREATE TABLE public.funcionario (
	id_funcionario INTEGER NOT NULL,
    id_usuario_cadastro INTEGER,
    id_empresa INTEGER NOT NULL,
    funcao SMALLINT NOT NULL,
    nome VARCHAR(60) NOT NULL,
    cpf CHAR(11) NOT NULL,
    rg VARCHAR(10) NOT NULL,
    celular CHAR(11) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    data_alteracao TIMESTAMP NOT NULL,
    CONSTRAINT funcionario_pk PRIMARY KEY (id_funcionario),
	CONSTRAINT funcionario_cpf_uk UNIQUE (cpf)
);

ALTER TABLE public.funcionario ADD CONSTRAINT usuario_funcionario_fk
FOREIGN KEY (id_funcionario)
REFERENCES public.usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.funcionario ADD CONSTRAINT usuario_cadastro_funcionario_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES public.usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.funcionario ADD CONSTRAINT empresa_funcionario_fk
FOREIGN KEY (id_empresa)
REFERENCES public.empresa (id_empresa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

CREATE TABLE acesso (
	series VARCHAR(64) NOT NULL,
	email VARCHAR(50) NOT NULL,
	token VARCHAR(64) NOT NULL,
	ultimo_acesso TIMESTAMP NOT NULL,
	CONSTRAINT acesso_pk PRIMARY KEY (series),
	CONSTRAINT acesso_token_uk UNIQUE (token),
	CONSTRAINT acesso_email_uk UNIQUE (email)
);

--password: 12345
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'gerente@casa-das-quentinhas.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'atendente@casa-das-quentinhas.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'entregador@casa-das-quentinhas.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'daniel@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'cintia@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'violeta@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'ariana@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'bianca@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'micaela@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'henrique@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'jeronimo@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'natal@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'antoniela@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'sabrina@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'carolina@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'nadia@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'evandro@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'elias@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'maximo@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'carlos@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'rafael@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'maximiano@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'julia@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);
INSERT INTO usuario(id_usuario, email, senha, tipo_usuario) VALUES (nextval('usuario_id_seq'),'guilherme@teste.com','$2a$10$9y4f/xNXOV4B9m8wBuXpZuG5cBvPIzbuwS.htxWs.PudI0XIeMAuC', 0);

INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Casa das Quentinhas', '76887213000149');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Serrano, Rezende e Aguiar', '78198941000179');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Soto-Domingues', '07583275000144');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Feliciano de Solano', '20974817000178');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Rivera-Caldeira', '20225681000101');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Neves e Associados', '19739410000187');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Caldeira, Velasques e Verdugo', '75989323000159');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Fonseca, Cortês e Duarte', '69203348000182');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Cruz e Flia.', '47637308000106');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Benites de Branco', '23244577000144');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Teles SRL', '39656327000151');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Soares S. de H.', '58229540000121');
INSERT INTO empresa(id_empresa, razao_social, cnpj) VALUES (nextval('empresa_id_seq'),'Serna e Lovato', '25869596000191');

INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (1, 0, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', null, 'César Antônio de Magalhães', '05948755678', '12345678', '31991012345');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (2, 1, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Leone José de Magalhães', '90596851561', '12345679', '31991012346');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (3, 2, 3,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Leandro Luzia de Magalhães', '68903888103', '12345670', '31991012347');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (4, 2, 4,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Daniel Júlio de Magalhães', '82157725288', '12345680', '31991012350');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (5, 2, 5,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Cintia Aparecida de Magalhães', '09203331921', '12345681', '31991012351');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (6, 2, 6,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Srta. Violeta Faria Neto', '25685519419', '12345682', '31991012352');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (7, 0, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Sra. Ariana Alessandra Rosa', '93920018192', '12345683', '31991012353');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (8, 0, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Bianca Batista Ferreira Filho', '53044371600', '12345684', '31991012354');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (9, 2, 2,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Srta. Micaela Franco Filho', '22971347206', '12345685', '31991012355');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (10, 2, 3,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Henrique Fábio Medina', '33142816682', '12345686', '31991012356');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (11, 1, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Sr. Jerônimo Ferminiano', '50509445110', '12345687', '31991012357');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (12, 1, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Natal Maia Martines Sobrinho', '37905482510', '12345688', '31991012358');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (13, 0, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Antonieta Rocha Madeira Jr.', '65500135260', '12345689', '31991012359');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (14, 0, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Sabrina Rocha Aragão Neto', '00772569215', '12345690', '31991012360');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (15, 2, 4,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Srta. Carolina Galindo Jr.', '55859062672', '12345691', '31991012361');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (16, 2, 2,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Nádia Serrano Vale', '34462923499', '12345692', '31991012362');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (17, 1, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Dr. Evandro Delvalle Filho', '07905702898', '12345693', '31991012363');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (18, 1, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Dante Elias Sepúlveda', '30687954193', '12345694', '31991012364');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (19, 0, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Máximo Leon Quintana Filho', '34603068409', '12345695', '31991012365');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (20, 0, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Carlos Oliveira da Silva', '43630098533', '12345696', '31991012366');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (21, 2, 2,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Rafael Cristóvão Dominato Neto', '17630167300', '12345697', '31991012367');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (22, 2, 3,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Maximiano Fonseca Vega Filho', '04671714671', '12345698', '31991012368');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (23, 1, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Dr. Júlia Cruz Sobrinho', '38008720565', '12345699', '31991012369');
INSERT INTO funcionario (id_funcionario, funcao, id_empresa, data_cadastro, data_alteracao, id_usuario_cadastro, nome, cpf, rg, celular) VALUES (24, 0, 1,'2016-12-26 18:45:00.0','2016-12-26 18:45:00.0', 1, 'Guilherme Galindo Sobrinho', '02441890212', '12345700', '31991012370');

SELECT u.email, f.nome FROM usuario u INNER JOIN funcionario f ON u.id_usuario=f.id_funcionario; 

