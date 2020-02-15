--DDL - Data Definition Language (Linguagem de Definição de Dados)
--DML - Data Manipulation Language (Linguagem de Manipulação de Dados)
--DCL - Data Control Language (Linguagem de Controle de Dados)
--DTL - Data Transaction Language (Linguagem de Transação de Dados)
--DQL - Data Query Language (Linguagem de Consulta de Dados)

DROP TABLE IF EXISTS cliente_pedido;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS empresa_pedido;
DROP TABLE IF EXISTS pedido_item;
DROP TABLE IF EXISTS pedido;
DROP TABLE IF EXISTS funcionario;
DROP TABLE IF EXISTS empresa;
DROP TABLE IF EXISTS produto;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS acesso;
DROP TABLE IF EXISTS cidade;
DROP TABLE IF EXISTS uf;
------------------------------------------------- Estado / UF --------------------------------------------------
DROP SEQUENCE IF EXISTS uf_id_seq;
CREATE SEQUENCE uf_id_seq;

CREATE TABLE uf (
                id_uf SMALLINT NOT NULL DEFAULT nextval('uf_id_seq'),
                nome VARCHAR(20) NOT NULL,
                sigla CHAR(2) NOT NULL,
                codigo_ibge CHAR(2) NOT NULL,
                CONSTRAINT uf_pk PRIMARY KEY (id_uf),
				CONSTRAINT uf_sigla_uk UNIQUE (sigla),
				CONSTRAINT uf_ibge_uk UNIQUE (codigo_ibge)
);
COMMENT ON TABLE uf IS 'Estado ou unidade federativa';
COMMENT ON COLUMN uf.id_uf IS 'Id do estado';
COMMENT ON COLUMN uf.nome IS 'Nome do estado';
COMMENT ON COLUMN uf.sigla IS 'Sigla do estado';
COMMENT ON COLUMN uf.codigo_ibge IS 'Código do estado de acordo com o IBGE';
------------------------------------------------- Cidade --------------------------------------------------
DROP SEQUENCE IF EXISTS cidade_id_seq;
CREATE SEQUENCE cidade_id_seq;

CREATE TABLE cidade (
                id_cidade INTEGER NOT NULL DEFAULT nextval('cidade_id_seq'),
                id_uf SMALLINT NOT NULL,
                nome VARCHAR(64) NOT NULL,
                codigo_ibge CHAR(7) NOT NULL,
                ddd CHAR(2) NOT NULL,
                CONSTRAINT cidade_pk PRIMARY KEY (id_cidade),
				CONSTRAINT cidade_ibge_uk UNIQUE (codigo_ibge)
);
COMMENT ON TABLE cidade IS 'Cidade';
COMMENT ON COLUMN cidade.id_cidade IS 'Id da cidade';
COMMENT ON COLUMN cidade.id_uf IS 'Id do estado';
COMMENT ON COLUMN cidade.codigo_ibge IS 'Código da cidade de acordo com o IBGE';
COMMENT ON COLUMN cidade.ddd IS 'DDD da cidade';
------------------------------------------------- Acesso --------------------------------------------------
CREATE TABLE acesso (
                series VARCHAR(64) NOT NULL,
                email VARCHAR(50) NOT NULL,
                token VARCHAR(64) NOT NULL,
                ultimo_acesso TIMESTAMP NOT NULL,
                CONSTRAINT acesso_pk PRIMARY KEY (series),
				CONSTRAINT acesso_token_uk UNIQUE (token)
);
------------------------------------------------- Usuário --------------------------------------------------
DROP SEQUENCE IF EXISTS usuario_id_seq;
CREATE SEQUENCE usuario_id_seq;

CREATE TABLE usuario (
                id_usuario INTEGER NOT NULL DEFAULT nextval('usuario_id_seq'),
                email VARCHAR(50) NOT NULL,
                senha VARCHAR(60) NOT NULL,
                tipo_usuario SMALLINT NOT NULL,
                CONSTRAINT usuario_pk PRIMARY KEY (id_usuario),
				CONSTRAINT usuario_email_uk UNIQUE (email)
);
COMMENT ON COLUMN usuario.tipo_usuario IS 'Tipo de usuário que pode ser FUNCIONARIO ou EMPRESA';
------------------------------------------------- Produto --------------------------------------------------
DROP SEQUENCE IF EXISTS produto_id_seq;
CREATE SEQUENCE produto_id_seq;

CREATE TABLE produto (
                id_produto INTEGER NOT NULL DEFAULT nextval('produto_id_seq'),
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
COMMENT ON TABLE produto IS 'Marmita vendida pela Casa das Quentinhas';
COMMENT ON COLUMN produto.id_usuario_cadastro IS 'Usuário que cadastrou o produto';
COMMENT ON COLUMN produto.tamanho IS 'Tamanho da marmita';
COMMENT ON COLUMN produto.codigo_ncm IS 'Nomenclatura Comum do Mercosul - NCM';
COMMENT ON COLUMN produto.codgio_cest IS 'Código Especificador da Substituição Tributária - CEST';
COMMENT ON COLUMN produto.data_cadastro IS 'Data de cadastro do produto';
COMMENT ON COLUMN produto.data_alteracao IS 'Data de alteração dos dados do produto';
------------------------------------------------- Pedido --------------------------------------------------
DROP SEQUENCE IF EXISTS pedido_id_seq;
CREATE SEQUENCE pedido_id_seq;

CREATE TABLE pedido (
                id_pedido BIGINT NOT NULL DEFAULT nextval('pedido_id_seq'),
                id_usuario_cadastro INTEGER NOT NULL,
				id_entregador INTEGER,
				tipo_cliente SMALLINT NOT NULL,
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
COMMENT ON COLUMN pedido.id_usuario_cadastro IS 'Usuário que cadastrou o pedido';
COMMENT ON COLUMN pedido.total_nota IS 'Valor total da nota';
COMMENT ON COLUMN pedido.icms_nota IS 'Valor ICMS da nota';
COMMENT ON COLUMN pedido.st_nota IS 'Valor ICMS ST nota';
COMMENT ON COLUMN pedido.forma_pagamento IS 'Forma de pagamento (0 - Pagamento à vista, 1 - Pagamento a prazo, 2 - Outros)';
COMMENT ON COLUMN pedido.consumidor_final IS 'Consumidor final (0 - Não, 1 - Sim)';
COMMENT ON COLUMN pedido.destino_operacao IS 'Destino da operação (1 - operação interna, 2 - operação interestadual, 3 - Operação com exterior)';
COMMENT ON COLUMN pedido.tipo_atendimento IS 'Tipo atendimento (0 - Não se aplica, 1 - Operação presencial, 2 - operação não presencial,  3 - Operação não presencial (pela teleatendimento), 9 - Operação não presencial (OUTROS))';
COMMENT ON COLUMN pedido.natureza_operacao IS 'Natureza da operação';
COMMENT ON COLUMN pedido.data_cadastro IS 'Data de cadastro do pedido';
COMMENT ON COLUMN pedido.data_alteracao IS 'Data da alteração dos dados dos dados do pedido';
------------------------------------------------- Item de Pedido --------------------------------------------------
CREATE TABLE pedido_item (
                id_pedido BIGINT NOT NULL,
                id_produto INTEGER NOT NULL,
                quantidade SMALLINT NOT NULL,
		custo REAL,
                codigo_cfop CHAR(5),
                codigo_icms CHAR(6),
                codigo_icms_origem CHAR(1),
                codigo_pis CHAR(2),
                codigo_cofins CHAR(2),
                codigo_csosn CHAR(3),
                CONSTRAINT pedido_item_pk PRIMARY KEY (id_pedido, id_produto)
);
COMMENT ON COLUMN pedido_item.id_pedido IS 'Id do pedido';
COMMENT ON COLUMN pedido_item.id_produto IS 'Id do produto';
COMMENT ON COLUMN pedido_item.codigo_cfop IS 'Código Fiscal de Operações e Prestação - CFOP';
COMMENT ON COLUMN pedido_item.codigo_icms IS 'ICMS - IMPOSTO SOBRE CIRCULAÇÃO DE MERCADORIAS E PRESTAÇÃO DE SERVIÇOS';
COMMENT ON COLUMN pedido_item.codigo_icms_origem IS 'ICMS';
COMMENT ON COLUMN pedido_item.codigo_pis IS 'Programa Integração Social - PIS';
COMMENT ON COLUMN pedido_item.codigo_cofins IS 'Contribuição para o Financiamento da Seguridade Social - COFINS';
COMMENT ON COLUMN pedido_item.codigo_csosn IS 'Codigo de Situacao da Operação no Simples Nacional - CSOSN';
------------------------------------------------- Empresa --------------------------------------------------
DROP SEQUENCE IF EXISTS empresa_id_seq;
CREATE SEQUENCE empresa_id_seq;

CREATE TABLE empresa (
                id_empresa INTEGER NOT NULL DEFAULT nextval('empresa_id_seq'),
                id_usuario_acesso INTEGER,
                id_usuario_cadastro INTEGER NOT NULL,
                id_cidade INTEGER NOT NULL,
                tipo_empresa SMALLINT,
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
                ponto_referencia VARCHAR(100),
                data_cadastro TIMESTAMP NOT NULL,
                data_alteracao TIMESTAMP NOT NULL,
                CONSTRAINT empresa_pk PRIMARY KEY (id_empresa),
				CONSTRAINT empresa_cnpj_uk UNIQUE (cnpj),
				CONSTRAINT empresa_email_uk UNIQUE (email)
);
COMMENT ON TABLE empresa IS 'Empresa pode ser um cliente ou prestadora de serviços da Casa das Quentinhas';
COMMENT ON COLUMN empresa.id_usuario_acesso IS 'A empresa pode ser um usuário do sistema';
COMMENT ON COLUMN empresa.id_usuario_cadastro IS 'Usuário que cadastrou a empresa';
COMMENT ON COLUMN empresa.id_cidade IS 'Id da cidade';
COMMENT ON COLUMN empresa.tipo_empresa IS 'Tipo de empresa que pode ser CLIENTE ou ENTREGA';
COMMENT ON COLUMN empresa.razao_social IS 'Nome/Razão social da empresa';
COMMENT ON COLUMN empresa.nome_fantasia IS 'Nome fantasia da empresa';
COMMENT ON COLUMN empresa.inscricao_estadual IS 'Inscrição estadual';
COMMENT ON COLUMN empresa.inscricao_estadual_st IS 'Inscrição estadual do subst. tributário';
COMMENT ON COLUMN empresa.inscricao_muncipal IS 'Inscricao municipal';
COMMENT ON COLUMN empresa.codigo_cnae IS 'Classificação Nacional de Atividades Econômicas - CNAE';
COMMENT ON COLUMN empresa.regime_tributario IS 'Regime tributário (Simples Nacional, Simples Nacional - excesso de sublimite de receita bruta, Regime Normal)';
COMMENT ON COLUMN empresa.data_cadastro IS 'Data de cadastro da empresa';
COMMENT ON COLUMN empresa.data_alteracao IS 'Data da alteracação dos dados da empresa';
------------------------------------------------- Funcionário --------------------------------------------------
CREATE TABLE funcionario (
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
                CONSTRAINT funcionario_pk PRIMARY KEY (id_funcionario),
				CONSTRAINT funcionario_cpf_uk UNIQUE (cpf)
);
COMMENT ON TABLE funcionario IS 'Funcionário da empresa Casa das Quentinhas';
COMMENT ON COLUMN funcionario.id_funcionario IS 'O funcionário é um usuário, com isso, tem o mesmo id';
COMMENT ON COLUMN funcionario.id_usuario_cadastro IS 'Usuário que cadastrou o funcionário';
COMMENT ON COLUMN funcionario.id_empresa IS 'Empresa a qual pertence o funcionário';
COMMENT ON COLUMN funcionario.funcao IS 'Função do funcionário que pode ser 1 - GERENTE, 2 - ATENDENTE ou 3 - ENTREGADOR';
COMMENT ON COLUMN funcionario.nome IS 'Nome do funcionário';
COMMENT ON COLUMN funcionario.cpf IS 'CPF do funcionário';
COMMENT ON COLUMN funcionario.rg IS 'Número da identidade do funcionário';
COMMENT ON COLUMN funcionario.celular IS 'O entregador precisa de um número de celular';
COMMENT ON COLUMN funcionario.data_cadastro IS 'Data de cadastro do funcionário';
COMMENT ON COLUMN funcionario.data_alteracao IS 'Data da alteração dos dados do funcionário';
------------------------------------------------- Pedido da Empresa --------------------------------------------------
CREATE TABLE empresa_pedido (
                id_empresa INTEGER NOT NULL,
                id_pedido BIGINT NOT NULL,
                CONSTRAINT empresa_pedido_pk PRIMARY KEY (id_empresa, id_pedido)
);
COMMENT ON TABLE empresa_pedido IS 'Relação entre o pedido e a empresa';
------------------------------------------------- Cliente --------------------------------------------------
DROP SEQUENCE IF EXISTS cliente_id_seq;
CREATE SEQUENCE cliente_id_seq;

CREATE TABLE cliente (
                id_cliente INTEGER NOT NULL DEFAULT nextval('cliente_id_seq'),
				id_usuario_acesso INTEGER,
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
                ponto_referencia VARCHAR(100),
                data_cadastro TIMESTAMP NOT NULL,
                data_alteracao TIMESTAMP NOT NULL,
                CONSTRAINT cliente_pk PRIMARY KEY (id_cliente),
				CONSTRAINT cliente_cpf_uk UNIQUE (cpf),
				CONSTRAINT cliente_email_uk UNIQUE (email)
);
COMMENT ON TABLE cliente IS 'Cliente da Casa da Marmitas';
COMMENT ON COLUMN cliente.id_usuario_acesso IS 'O usuário pode ser um usuário do sistema';
COMMENT ON COLUMN cliente.id_usuario_cadastro IS 'Usuário que cadastrou o cliente';
COMMENT ON COLUMN cliente.id_cidade IS 'Id da cidade';
COMMENT ON COLUMN cliente.cpf IS 'CPF do cliente';
COMMENT ON COLUMN cliente.tipo_contribuinte IS 'Tipo de contribuinte (1 - Contribuinte ICMS, 2 - Contribuinte ISENTO, 9 - Não contribuinte)';
COMMENT ON COLUMN cliente.email IS 'E-mail do cliente';
COMMENT ON COLUMN cliente.data_cadastro IS 'Data de cadastro do cliente';
COMMENT ON COLUMN cliente.data_alteracao IS 'Data da alteração dos dados do cliente';
------------------------------------------------- Pedido do Cliente --------------------------------------------------
CREATE TABLE public.cliente_pedido (
                id_cliente INTEGER NOT NULL,
                id_pedido BIGINT NOT NULL,
                CONSTRAINT cliente_pedido_pk PRIMARY KEY (id_cliente, id_pedido)
);
COMMENT ON TABLE public.cliente_pedido IS 'Relação entre o pedido e o cliente';