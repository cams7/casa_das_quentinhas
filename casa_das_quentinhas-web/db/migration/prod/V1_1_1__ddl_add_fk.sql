------------------------------------------------- Chaves estrangeiras --------------------------------------------------
ALTER TABLE cidade ADD CONSTRAINT uf_cidade_fk
FOREIGN KEY (id_uf)
REFERENCES uf (id_uf)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE empresa ADD CONSTRAINT cidade_empresa_fk
FOREIGN KEY (id_cidade)
REFERENCES cidade (id_cidade)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE empresa ADD CONSTRAINT usuario_cadastro_empresa_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE empresa ADD CONSTRAINT usuario_acesso_empresa_fk
FOREIGN KEY (id_usuario_acesso)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE funcionario ADD CONSTRAINT usuario_funcionario_fk
FOREIGN KEY (id_funcionario)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE funcionario ADD CONSTRAINT usuario_cadastro_funcionario_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE funcionario ADD CONSTRAINT empresa_funcionario_fk
FOREIGN KEY (id_empresa)
REFERENCES empresa (id_empresa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE produto ADD CONSTRAINT usuario_produto_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE cliente ADD CONSTRAINT cidade_cliente_fk
FOREIGN KEY (id_cidade)
REFERENCES cidade (id_cidade)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE cliente ADD CONSTRAINT usuario_cadastro_cliente_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE cliente ADD CONSTRAINT usuario_acesso_cliente_fk
FOREIGN KEY (id_usuario_acesso)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE pedido ADD CONSTRAINT usuario_pedido_fk
FOREIGN KEY (id_usuario_cadastro)
REFERENCES usuario (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE pedido ADD CONSTRAINT entregador_pedido_fk
FOREIGN KEY (id_entregador)
REFERENCES funcionario (id_funcionario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE empresa_pedido ADD CONSTRAINT pedido_empresa_pedido_fk
FOREIGN KEY (id_pedido)
REFERENCES pedido (id_pedido)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE empresa_pedido ADD CONSTRAINT empresa_empresa_pedido_fk
FOREIGN KEY (id_empresa)
REFERENCES empresa (id_empresa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE cliente_pedido ADD CONSTRAINT pedido_cliente_pedido_fk
FOREIGN KEY (id_pedido)
REFERENCES pedido (id_pedido)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE cliente_pedido ADD CONSTRAINT cliente_cliente_pedido_fk
FOREIGN KEY (id_cliente)
REFERENCES cliente (id_cliente)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE pedido_item ADD CONSTRAINT produto_pedido_item_fk
FOREIGN KEY (id_produto)
REFERENCES produto (id_produto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE pedido_item ADD CONSTRAINT pedido_pedido_item_fk
FOREIGN KEY (id_pedido)
REFERENCES pedido (id_pedido)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;