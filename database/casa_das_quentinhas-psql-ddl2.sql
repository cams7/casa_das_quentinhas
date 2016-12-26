
CREATE SEQUENCE public.uf_id_seq;

CREATE TABLE public.uf (
                id_uf SMALLINT NOT NULL DEFAULT nextval('public.uf_id_seq'),
                nome VARCHAR(64) NOT NULL,
                sigla CHAR(2) NOT NULL,
                codigo_ibge CHAR(2) NOT NULL,
                CONSTRAINT uf_pk PRIMARY KEY (id_uf)
);
COMMENT ON TABLE public.uf IS 'Estado ou unidade federativa.';
COMMENT ON COLUMN public.uf.id_uf IS 'Id do estado.';
COMMENT ON COLUMN public.uf.nome IS 'Nome do estado.';
COMMENT ON COLUMN public.uf.sigla IS 'Sigla do estado.';
COMMENT ON COLUMN public.uf.codigo_ibge IS 'C�digo do estado de acordo com o IBGE.';


ALTER SEQUENCE public.uf_id_seq OWNED BY public.uf.id_uf;

CREATE SEQUENCE public.cidade_id_seq;

CREATE TABLE public.cidade (
                id_cidade INTEGER NOT NULL DEFAULT nextval('public.cidade_id_seq'),
                id_uf SMALLINT NOT NULL,
                nome VARCHAR(64) NOT NULL,
                codigo_ibge CHAR(7) NOT NULL,
                ddd CHAR(2) NOT NULL,
                CONSTRAINT cidade_pk PRIMARY KEY (id_cidade)
);
COMMENT ON TABLE public.cidade IS 'Cidade.';
COMMENT ON COLUMN public.cidade.id_cidade IS 'Id da cidade.';
COMMENT ON COLUMN public.cidade.id_uf IS 'Id do estado.';
COMMENT ON COLUMN public.cidade.codigo_ibge IS 'C�digo da cidade de acordo com o IBGE.';
COMMENT ON COLUMN public.cidade.ddd IS 'DDD da cidade.';


ALTER SEQUENCE public.cidade_id_seq OWNED BY public.cidade.id_cidade;

CREATE TABLE public.acesso (
                series VARCHAR(64) NOT NULL,
                email VARCHAR(50) NOT NULL,
                token VARCHAR(64) NOT NULL,
                ultimo_acesso TIMESTAMP NOT NULL,
                CONSTRAINT acesso_pk PRIMARY KEY (series),
				CONSTRAINT acesso_email_uk UNIQUE (email),
				CONSTRAINT acesso_token_uk UNIQUE (token)
);

CREATE SEQUENCE public.usuario_id_seq;

CREATE TABLE public.usuario (
                id_usuario INTEGER NOT NULL DEFAULT nextval('public.usuario_id_seq'),
                email VARCHAR(50) NOT NULL,
                senha VARCHAR(60) NOT NULL,
                tipo_usuario SMALLINT NOT NULL,
                CONSTRAINT usuario_pk PRIMARY KEY (id_usuario)
);
COMMENT ON COLUMN public.usuario.tipo_usuario IS 'Tipo de usu�rio que pode ser FUNCION�RIO ou EMPRESA.';


ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id_usuario;

CREATE SEQUENCE public.produto_id_seq;

CREATE TABLE public.produto (
                id_produto INTEGER NOT NULL DEFAULT nextval('public.produto_id_seq'),
                id_usuario_cadastro INTEGER NOT NULL,
                nome VARCHAR(60) NOT NULL,
                ingredientes TEXT NOT NULL,
                custo REAL NOT NULL,
                tamanho SMALLINT NOT NULL,
                codigo_ncm CHAR(8),
                codgio_cest CHAR(7),
                data_cadastro TIMESTAMP NOT NULL,
                data_alteracao TIMESTAMP NOT NULL,
                CONSTRAINT produto_pk PRIMARY KEY (id_produto)
);
COMMENT ON TABLE public.produto IS 'Marmita vendida pela Casa das Quentinhas.';
COMMENT ON COLUMN public.produto.id_usuario_cadastro IS 'Usu�rio que cadastrou o produto.';
COMMENT ON COLUMN public.produto.tamanho IS 'Tamanho da marmita.';
COMMENT ON COLUMN public.produto.codigo_ncm IS 'Nomenclatura Comum do Mercosul - NCM';
COMMENT ON COLUMN public.produto.codgio_cest IS 'C�digo Especificador da Substitui��o Tribut�ria - CEST';
COMMENT ON COLUMN public.produto.data_cadastro IS 'Data de cadastro do produto.';
COMMENT ON COLUMN public.produto.data_alteracao IS 'Data de altera��o dos dados do produto.';


ALTER SEQUENCE public.produto_id_seq OWNED BY public.produto.id_produto;

CREATE SEQUENCE public.pedido_id_seq;

CREATE TABLE public.pedido (
                id_pedido BIGINT NOT NULL DEFAULT nextval('public.pedido_id_seq'),
                id_usuario_cadastro INTEGER NOT NULL,
                quantidade_total SMALLINT NOT NULL,
                total_nota REAL NOT NULL,
                icms_nota REAL,
                st_nota REAL,
                forma_pagamento SMALLINT,
                consumidor_final BOOLEAN,
                destino_operacao SMALLINT,
                tipo_atendimento SMALLINT,
                natureza_operacao VARCHAR(20),
                situacao_pedido SMALLINT NOT NULL,
                data_cadastro TIMESTAMP NOT NULL,
                data_alteracao TIMESTAMP NOT NULL,
                CONSTRAINT pedido_pk PRIMARY KEY (id_pedido)
);
COMMENT ON COLUMN public.pedido.id_usuario_cadastro IS 'Usu�rio que cadastrou o pedido.';
COMMENT ON COLUMN public.pedido.total_nota IS 'Valor total da nota.';
COMMENT ON COLUMN public.pedido.icms_nota IS 'Valor ICMS da nota.';
COMMENT ON COLUMN public.pedido.st_nota IS 'Valor ICMS ST nota.';
COMMENT ON COLUMN public.pedido.forma_pagamento IS 'Forma de pagamento (0 - Pagamento � vista, 1 - Pagamento a prazo, 2 - Outros)';
COMMENT ON COLUMN public.pedido.consumidor_final IS 'Consumidor final (0 - N�o, 1 - Sim)';
COMMENT ON COLUMN public.pedido.destino_operacao IS 'Destino da opera��o (1 - opera��o interna, 2 - opera��o interestadual, 3 - Opera��o com exterior)';
COMMENT ON COLUMN public.pedido.tipo_atendimento IS 'Tipo atendimento (0 - N�o se aplica, 1 - Opera��o presencial, 2 - opera��o N�O presencial,  3 - Opera��o N�O presencial (pela teleatendimento), 9 - Opera��o N�o presencial (OUTROS))';
COMMENT ON COLUMN public.pedido.natureza_operacao IS 'Natureza da opera��o.';
COMMENT ON COLUMN public.pedido.data_cadastro IS 'Data de cadastro do pedido.';
COMMENT ON COLUMN public.pedido.data_alteracao IS 'Data da altera��o dos dados dos dados do pedido.';


ALTER SEQUENCE public.pedido_id_seq OWNED BY public.pedido.id_pedido;

CREATE TABLE public.pedido_item (
                id_pedido BIGINT NOT NULL,
                id_produto INTEGER NOT NULL,
                quantidade SMALLINT NOT NULL,
                codigo_cfop CHAR(5),
                codigo_icms CHAR(6),
                codigo_icms_origem CHAR(1),
                codigo_pis CHAR(2),
                codigo_cofins CHAR(2),
                codigo_csosn CHAR(3),
                CONSTRAINT pedido_item_pk PRIMARY KEY (id_pedido, id_produto)
);
COMMENT ON COLUMN public.pedido_item.id_pedido IS 'Id do pedido.';
COMMENT ON COLUMN public.pedido_item.id_produto IS 'Id do produto.';
COMMENT ON COLUMN public.pedido_item.codigo_cfop IS 'C�digo Fiscal de Opera��es e Presta��o - CFOP';
COMMENT ON COLUMN public.pedido_item.codigo_icms IS 'ICMS - IMPOSTO SOBRE CIRCULA��O DE MERCADORIAS E PRESTA��O DE SERVI�OS';
COMMENT ON COLUMN public.pedido_item.codigo_icms_origem IS 'ICMS';
COMMENT ON COLUMN public.pedido_item.codigo_pis IS 'Programa Integra��o Social - PIS';
COMMENT ON COLUMN public.pedido_item.codigo_cofins IS 'Contribui��o para o Financiamento da Seguridade Social � COFINS';
COMMENT ON COLUMN public.pedido_item.codigo_csosn IS 'Codigo de Situacao da Operacao no Simples Nacional - CSOSN';


CREATE SEQUENCE public.empresa_id_seq;

CREATE TABLE public.empresa (
                id_empresa INTEGER NOT NULL DEFAULT nextval('public.empresa_id_seq'),
                id_usuario_acesso INTEGER,
                id_usuario_cadastro INTEGER NOT NULL,
                id_cidade INTEGER NOT NULL,
                tipo_empresa SMALLINT NOT NULL,
                razao_social VARCHAR(60) NOT NULL,
                nome_fantasia VARCHAR(60),
                cnpj VARCHAR(14) NOT NULL,
                inscricao_estadual VARCHAR(15),
                inscricao_estadual_st VARCHAR(15),
                inscricao_muncipal VARCHAR(20),
                codigo_cnae CHAR(9),
                regime_tributario SMALLINT,
                email VARCHAR(50) NOT NULL,
                telefone VARCHAR(11) NOT NULL,
                cep VARCHAR(8) NOT NULL,
                bairro VARCHAR(60) NOT NULL,
                logradouro VARCHAR(100) NOT NULL,
                numero_imovel VARCHAR(30) NOT NULL,
                complemento_endereco VARCHAR(30),
                ponto_referencia VARCHAR(30),
                data_cadastro TIMESTAMP NOT NULL,
                data_alteracao TIMESTAMP NOT NULL,
                CONSTRAINT empresa_pk PRIMARY KEY (id_empresa)
);
COMMENT ON TABLE public.empresa IS 'Empresa pode ser um cliente ou prestadora de servi�os da Casa das Quentinhas.';
COMMENT ON COLUMN public.empresa.id_usuario_acesso IS 'A empresa pode ser um usu�rio do sistema.';
COMMENT ON COLUMN public.empresa.id_usuario_cadastro IS 'Usu�rio que cadastrou a empresa.';
COMMENT ON COLUMN public.empresa.id_cidade IS 'Id da cidade.';
COMMENT ON COLUMN public.empresa.tipo_empresa IS 'Tipo de empresa que pode ser CLIENTE ou ENTREGA.';
COMMENT ON COLUMN public.empresa.razao_social IS 'Nome/Raz�o social da empresa.';
COMMENT ON COLUMN public.empresa.nome_fantasia IS 'Nome fantasia da empresa.';
COMMENT ON COLUMN public.empresa.inscricao_estadual IS 'Inscri��o estadual.';
COMMENT ON COLUMN public.empresa.inscricao_estadual_st IS 'Inscri��o estadual do subst. tribut�rio.';
COMMENT ON COLUMN public.empresa.inscricao_muncipal IS 'Inscricao municipal.';
COMMENT ON COLUMN public.empresa.codigo_cnae IS 'Classifica��o Nacional de Atividades Econ�micas � CNAE';
COMMENT ON COLUMN public.empresa.regime_tributario IS 'Regime tribut�rio (Simples Nacional, Simples Nacional - excesso de sublimite de receita bruta, Regime Normal)';
COMMENT ON COLUMN public.empresa.data_cadastro IS 'Data de cadastro da empresa.';
COMMENT ON COLUMN public.empresa.data_alteracao IS 'Data da alteraca��o dos dados da empresa.';


ALTER SEQUENCE public.empresa_id_seq OWNED BY public.empresa.id_empresa;

CREATE TABLE public.funcionario (
                id_funcionario INTEGER NOT NULL,
                id_usuario_cadastro INTEGER NOT NULL,
                id_empresa INTEGER NOT NULL,
                funcao SMALLINT NOT NULL,
                nome VARCHAR(60) NOT NULL,
                cpf CHAR(11) NOT NULL,
                rg VARCHAR(10) NOT NULL,
                celular CHAR(11) NOT NULL,
                data_cadastro TIMESTAMP NOT NULL,
                data_alteracao TIMESTAMP NOT NULL,
                CONSTRAINT funcionario_pk PRIMARY KEY (id_funcionario)
);
COMMENT ON TABLE public.funcionario IS 'Funcion�rio da empresa Casa das Quentinhas.';
COMMENT ON COLUMN public.funcionario.id_funcionario IS 'O funcion�rio � um usu�rio, com isso, tem o mesmo id.';
COMMENT ON COLUMN public.funcionario.id_usuario_cadastro IS 'Usu�rio que cadastrou o funcion�rio.';
COMMENT ON COLUMN public.funcionario.id_empresa IS 'Empresa a qual pertence o funcion�rio.';
COMMENT ON COLUMN public.funcionario.funcao IS 'Fun��o do funcion�rio que pode ser 1 - GERENTE, 2 - ATENDENTE ou 3 - ENTREGADOR.';
COMMENT ON COLUMN public.funcionario.nome IS 'Nome do funcion�rio.';
COMMENT ON COLUMN public.funcionario.cpf IS 'CPF do funcion�rio.';
COMMENT ON COLUMN public.funcionario.rg IS 'N�mero da identidade do funcion�rio.';
COMMENT ON COLUMN public.funcionario.celular IS 'O entregador precisa de um n�mero de celular.';
COMMENT ON COLUMN public.funcionario.data_cadastro IS 'Data de cadastro do funcion�rio.';
COMMENT ON COLUMN public.funcionario.data_alteracao IS 'Data da altera��o dos dados do funcion�rio.';


CREATE TABLE public.empresa_pedido (
                id_empresa INTEGER NOT NULL,
                id_pedido BIGINT NOT NULL,
                CONSTRAINT empresa_pedido_pk PRIMARY KEY (id_empresa, id_pedido)
);
COMMENT ON TABLE public.empresa_pedido IS 'Rela��o entre o pedido e a empresa.';


CREATE SEQUENCE public.cliente_id_seq;

CREATE TABLE public.cliente (
                id_cliente INTEGER NOT NULL DEFAULT nextval('public.cliente_id_seq'),
                id_usuario_cadastro INTEGER NOT NULL,
                id_cidade INTEGER NOT NULL,
                nome VARCHAR(60) NOT NULL,
                telefone VARCHAR(11) NOT NULL,
                cpf CHAR(11),
                tipo_contribuinte SMALLINT,
                email VARCHAR(50),
                cep VARCHAR(8) NOT NULL,
                nascimento DATE,
                bairro VARCHAR(60) NOT NULL,
                logradouro VARCHAR(100) NOT NULL,
                numero_residencial VARCHAR(30) NOT NULL,
                complemento_endereco VARCHAR(30),
                ponto_referencia VARCHAR(30),
                data_cadastro TIMESTAMP NOT NULL,
                data_alteracao TIMESTAMP NOT NULL,
                CONSTRAINT cliente_pk PRIMARY KEY (id_cliente)
);
COMMENT ON TABLE public.cliente IS 'Cliente da Casa da Marmitas.';
COMMENT ON COLUMN public.cliente.id_usuario_cadastro IS 'Usu�rio que cadastrou o cliente.';
COMMENT ON COLUMN public.cliente.id_cidade IS 'Id da cidade.';
COMMENT ON COLUMN public.cliente.cpf IS 'CPF do cliente.';
COMMENT ON COLUMN public.cliente.tipo_contribuinte IS 'Tipo de contribuinte (1 - Contribuinte ICMS, 2 - Contribuinte ISENTO, 9 - N�o contribuinte)';
COMMENT ON COLUMN public.cliente.email IS 'E-mail do cliente';
COMMENT ON COLUMN public.cliente.data_cadastro IS 'Data de cadastro do cliente.';
COMMENT ON COLUMN public.cliente.data_alteracao IS 'Data da altera��o dos dados do cliente.';


ALTER SEQUENCE public.cliente_id_seq OWNED BY public.cliente.id_cliente;

CREATE TABLE public.cliente_pedido (
                id_cliente INTEGER NOT NULL,
                id_pedido BIGINT NOT NULL,
                CONSTRAINT cliente_pedido_pk PRIMARY KEY (id_cliente, id_pedido)
);
COMMENT ON TABLE public.cliente_pedido IS 'Rela��o entre o pedido e o cliente.';


ALTER TABLE public.cidade ADD CONSTRAINT uf_cidade_fk
FOREIGN KEY (id_uf)
REFERENCES public.uf (id_uf)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empresa ADD CONSTRAINT cidade_empresa_fk
FOREIGN KEY (id_cidade)
REFERENCES public.cidade (id_cidade)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cliente ADD CONSTRAINT cidade_cliente_fk
FOREIGN KEY (id_cidade)
REFERENCES public.cidade (id_cidade)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empresa ADD CONSTRAINT usuario_cadastro_empresa_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES public.usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cliente ADD CONSTRAINT usuario_cliente_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES public.usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pedido ADD CONSTRAINT usuario_pedido_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES public.usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.produto ADD CONSTRAINT usuario_produto_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES public.usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

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

ALTER TABLE public.empresa ADD CONSTRAINT usuario_empresa_fk
FOREIGN KEY (id_usuario_acesso)
REFERENCES public.usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pedido_item ADD CONSTRAINT produto_pedido_item_fk
FOREIGN KEY (id_produto)
REFERENCES public.produto (id_produto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pedido_item ADD CONSTRAINT pedido_pedido_item_fk
FOREIGN KEY (id_pedido)
REFERENCES public.pedido (id_pedido)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cliente_pedido ADD CONSTRAINT pedido_cliente_pedido_fk
FOREIGN KEY (id_pedido)
REFERENCES public.pedido (id_pedido)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empresa_pedido ADD CONSTRAINT pedido_empresa_pedido_fk
FOREIGN KEY (id_pedido)
REFERENCES public.pedido (id_pedido)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empresa_pedido ADD CONSTRAINT empresa_empresa_pedido_fk
FOREIGN KEY (id_empresa)
REFERENCES public.empresa (id_empresa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.funcionario ADD CONSTRAINT empresa_funcionario_fk
FOREIGN KEY (id_empresa)
REFERENCES public.empresa (id_empresa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cliente_pedido ADD CONSTRAINT cliente_cliente_pedido_fk
FOREIGN KEY (id_cliente)
REFERENCES public.cliente (id_cliente)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
