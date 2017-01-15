--Empresas: 7, 8, 11, 13
--Funcionários: 1, 2, 7, 8, 11, 12, 13, 14, 17, 18, 19, 20, 23 24
--Clientes: 1, 2, 3, 4, 5, 6, 7, 8, 9
--Produtos: 1(18,00), 2(15,00), 3(12,25), 4(15,00), 5(11,50), 6(9,00), 7(14,00), 8(9,50), 9(7,00), 10(10,00), 11(7,25), 12(5,00)

--Pedido 1
--18,00<1> + 15,00<2> + 12,25<3> + 15,00<4> + 11,50<5> + 9,00<6> + 14,00<7> + 9,50<8> + 7,00<9> + 10,00<10> + 7,25<11> + 5,00<12> =  133,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 1, 0, 12, 133.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (1, 1);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 2, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 3, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 4, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 5, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 6, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 7, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 8, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 9, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 10, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 11, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (1, 12, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 2
-- 1 x 18,00<1> + 2 x 15,00<2> + 3 x 12,25<3> + 4 x 15,00<4> + 5 x 11,50<5> + 6 x 9,00<6> + 7 x 14,00<7> + 8 x 9,50<8> + 9 x 7,00<9> + 10 x 10,00<10> + 11 x 7,25<11> + 12 x 5,00<12> = 733
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 78, 733, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (7, 2);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 2, 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 3, 3, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 4, 4, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 5, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 6, 6, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 7, 7, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 8, 8, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 9, 9, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 10, 10, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 11, 11, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (2, 12, 12, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 3
--4 x 18,00<1> + 2 x 15,00<2> + 1 x 11,50<5> + 1 x 9,00<6> + 2 x 9,50<8> + 5 x 7,00<9> + 20 x 10,00<10> + 12 x 5,00<12> = 436,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 7, 0, 47, 436.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (2,3);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (3, 1, 4, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (3, 2, 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (3, 5, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (3, 6, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (3, 8, 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (3, 9, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (3, 10, 20, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (3, 12, 12, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 4
--5 x 15,00<4> + 10 x 9,50<8> + 1 x 7,00<9> + 3 x 7,25<11> + 1 x 5,00<12> =  203,75
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 8, 0, 20, 203.75, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (1, 4);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (4, 4, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (4, 8, 10, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (4, 9, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (4, 11, 3, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (4, 12, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 5
--3 x 12,25<3> + 2 x 11,50<5> + 9 x 9,50<8> + 7 x 7,25<11> =  196
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 11, 0, 21, 196, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (3, 5);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (5, 3, 3, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (5, 5, 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (5, 8, 9, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (5, 11, 7, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 6
--1 x 15,00<4> + 2 x 5,00<12> =  25,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 12, 0, 3, 25, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (2, 6);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (6, 4, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (6, 12, 2, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 7
--1 x 11,50<5> = 11,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 13, 0, 1, 11.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (1, 7);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (7, 5, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 8
--8 x 15,00<4> + 1 x 9,00<6> + 6 x 7,00<9> =  171,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 14, 0, 15, 171, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (4, 8);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (8, 4, 8, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (8, 6, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (8, 9, 6, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 9
--12 x 18,00<1> + 11 x 15,00<2> + 10 x 12,25<3> + 9 x 15,00<4> + 8 x 11,50<5> + 7 x 9,00<6> + 6 x 14,00<7> + 5 x 9,50<8> + 4 x 7,00<9> + 3 x 10,00<10> + 2 x 7,25<11> + 1 x 5,00<12> =  1002,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 17, 0, 78, 1002.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (5, 9);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 1, 12, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 2, 11, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 3, 10, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 4, 9, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 5, 8, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 6, 7, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 7, 6, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 8, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 9, 4, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 10, 3, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 11, 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (9, 12, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 10
--8 x 18,00<1> + 2 x 15,00<2> + 1 x 15,00<4> + 3 x 11,50<5> + 10 x 14,00<7> + 25 x 9,50<8> + 8 x 10,00<10> + 4 x 7,25<11> =  710,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 18, 0, 61, 710, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (4, 10);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (10, 1, 8, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (10, 2, 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (10, 4, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (10, 5, 3, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (10, 7, 10, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (10, 8, 25, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (10, 10, 8, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (10, 11, 4, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 11
--5 x 15,00<4> + 4 x 11,50<5> + 7 x 9,50<8> + 9 x 7,00<9> =  250,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 19, 0, 25, 250.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (6, 11);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (11, 4, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (11, 5, 4, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (11, 8, 7, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (11, 9, 9, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 12
--1 x 11,50<5> + 8 x 9,00<6> + 6 x 14,00<7> =  167,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 20, 0, 15, 167.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (6, 12);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (12, 5, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (12, 6, 8, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (12, 7, 6, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 13
--6 x 18,00<1> =  108,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 23, 0, 6, 108, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (7, 13);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (13, 1, 6, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 14
--5 x 11,50<5> + 8 x 9,00<6> =  129,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 24, 0, 13, 129.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (8, 14);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (14, 5, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (14, 6, 8, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 15
--30 x 18,00<1> + 40 x 15,00<2> + 5 x 14,00<7> + 20 x 9,50<8> + 25 x 7,25<11> = 1581,25 
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 1, 0, 120, 1581.25, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (9, 15);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (15, 1, 30, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (15, 2, 40, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (15, 7, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (15, 8, 20, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (15, 11, 25, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 16
--4 x 18,00<1> + 1 x 11,50<5> + 25 x 9,00<6> = 308,50 
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 1, 0, 30, 308.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (9, 16);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (16, 1, 4, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (16, 5, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (16, 6, 25, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 17
--8 x 12,25<3> + 8 x 15,00<4> + 8 x 11,50<5> = 310 
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 0, 24, 310, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (8, 17);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (17, 3, 8, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (17, 4, 8, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (17, 5, 8, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 18
--1 x 18,00<1> + 9 x 15,00<2> + 7 x 9,00<6> + 11 x 14,00<7> + 15 x 5,00<12> = 445 
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 1, 0, 43, 445, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (7, 18);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (18, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (18, 2, 9, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (18, 6, 7, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (18, 7, 11, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (18, 12, 15, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 19
--1 x 12,25<3> + 1 x 15,00<4> =  27,25
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 0, 2, 27.25, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."cliente_pedido" (id_cliente, id_pedido) VALUES (8, 19);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (19, 3, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (19, 4, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 20
-- 18,00<1> = 18,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 1, 18, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (8, 20);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (20, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 21
-- 2 x 15,00<2> = 30,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 20, 1, 2, 30, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (11, 21);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (21, 2, 2, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 22
-- 12,25<3> = 12,25
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 21, 1, 1, 12.25, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (13, 22);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (22, 3, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 23
-- 4 x 15,00<4> = 60,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 4, 60, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (7, 23);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (23, 4, 4, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 24
-- 11,50<5> = 11,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 23, 1, 1, 11.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (7, 24);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (24, 5, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 25
-- 6 x 9,00<6> = 54,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 6, 54, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (8, 25);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (25, 6, 6, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 26
-- 5 x 14,00<7> = 70,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 25, 1, 5, 70, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (7, 26);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (26, 7, 5, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 27
-- 5 x 14,00<7> = 70,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 25, 1, 5, 70, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (8, 27);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (27, 7, 5, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 28
-- 9,50<8> = 9,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 1, 9.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (7, 28);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (28, 8, 1, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 29
-- 7 x 7,00<9> = 49,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 7, 49, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (11, 29);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (29, 9, 7, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 30
-- 3 x 10,00<10> = 30,00
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 3, 30, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (13, 30);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (30, 10, 3, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 31
-- 10 x 7,25<11> = 72,50
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 10, 72.5, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (7, 31);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (31, 11, 10, NULL, NULL, NULL, NULL, NULL, NULL);
--Pedido 32
-- 12 x 5,00<12> = 60
INSERT INTO "public"."pedido" (id_pedido, id_usuario_cadastro, tipo_cliente, quantidade_total, total_nota, icms_nota, st_nota, forma_pagamento, consumidor_final, destino_operacao, tipo_atendimento, natureza_operacao, situacao_pedido, data_cadastro, data_alteracao) VALUES (nextval('pedido_id_seq'), 2, 1, 12, 60, NULL, NULL, 1, NULL, 0, 3, NULL, 0, '2017-01-02 15:05:00.0','2017-01-02 15:05:00.0');

INSERT INTO "public"."empresa_pedido" (id_empresa, id_pedido) VALUES (11, 32);

INSERT INTO "public"."pedido_item" (id_pedido, id_produto, quantidade, codigo_cfop, codigo_icms, codigo_icms_origem, codigo_pis, codigo_cofins, codigo_csosn) VALUES (32, 12, 12, NULL, NULL, NULL, NULL, NULL, NULL);