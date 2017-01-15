select
p.id_pedido as "id", 
p.data_cadastro as "manutencao.cadastro", 
p.total_nota as "custo",  
p.quantidade_total as "quantidade",
CASE p.tipo_cliente WHEN 1 THEN 'PESSOA_JURIDICA' ELSE 'PESSOA_FISICA' END as "tipoCliente",
c.bairro as "cliente.endereco.bairro", 
c.nome as "cliente.nome", 
cc.nome as "cliente.cidade.nome",
e.bairro as "empresa.endereco.bairro", 
e.razao_social as "empresa.razaoSocial", 
ec.nome as "empresa.cidade.nome"
from pedido p
left join cliente_pedido cp on p.id_pedido=cp.id_pedido
left join cliente c on c.id_cliente=cp.id_cliente
left join cidade cc on c.id_cidade=cc.id_cidade
left join empresa_pedido ep on p.id_pedido=ep.id_pedido
left join empresa e on e.id_empresa=ep.id_empresa
left join cidade ec on e.id_cidade=ec.id_cidade
order by "empresa.cidade.nome" asc, "cliente.cidade.nome" asc;

select
count(p.id_pedido) as "pedidos",
sum(p.total_nota) as "custo_total",  
sum(p.quantidade_total) as "quantidade_total", 
c.nome as "cliente.nome", 
e.razao_social as "empresa.razaoSocial"
from pedido p
left join cliente_pedido cp on p.id_pedido=cp.id_pedido
left join cliente c on c.id_cliente=cp.id_cliente
left join empresa_pedido ep on p.id_pedido=ep.id_pedido
left join empresa e on e.id_empresa=ep.id_empresa
group by c.id_cliente, e.id_empresa ORDER BY "custo_total" desc;

select * from (select
p.id_pedido as "id", 
p.data_cadastro as "manutencao.cadastro", 
p.total_nota as "custo",  
p.quantidade_total as "quantidade", 
CASE p.tipo_cliente WHEN 1 THEN 'PESSOA_JURIDICA' ELSE 'PESSOA_FISICA' END as "tipoCliente",
c.nome as "cliente.nome", 
c.bairro as "cliente.endereco.bairro", 
cc.nome as "cliente.cidade.nome"
from pedido p
inner join cliente_pedido cp on p.id_pedido=cp.id_pedido
inner join cliente c on c.id_cliente=cp.id_cliente
inner join cidade cc on c.id_cidade=cc.id_cidade
union
select
p.id_pedido as "id", 
p.data_cadastro as "manutencao.cadastro", 
p.total_nota as "custo",  
p.quantidade_total as "quantidade", 
CASE p.tipo_cliente WHEN 1 THEN 'PESSOA_JURIDICA' ELSE 'PESSOA_FISICA' END as "tipoCliente",
e.razao_social as "cliente.nome", 
e.bairro as "cliente.endereco.bairro", 
ec.nome as "cliente.cidade.nome"
from pedido p
inner join empresa_pedido ep on p.id_pedido=ep.id_pedido
inner join empresa e on e.id_empresa=ep.id_empresa
inner join cidade ec on e.id_cidade=ec.id_cidade) p order by "cliente.cidade.nome" asc, "cliente.nome" asc, "id" desc;

select 
count(id) as "pedidos", 
sum(custo) as "custo_total", 
sum(quantidade) as "quantidade_total" 
from (select
p.id_pedido as "id", 
p.total_nota as "custo",  
p.quantidade_total as "quantidade", 
c.id_cliente as "cliente.id",
c.nome as "cliente.nome"
from pedido p
inner join cliente_pedido cp on p.id_pedido=cp.id_pedido
inner join cliente c on c.id_cliente=cp.id_cliente
union
select
p.id_pedido as "id", 
p.total_nota as "custo",  
p.quantidade_total as "quantidade", 
e.id_empresa as "cliente.id",
e.razao_social as "cliente.nome"
from pedido p
inner join empresa_pedido ep on p.id_pedido=ep.id_pedido
inner join empresa e on e.id_empresa=ep.id_empresa) p 
GROUP BY "cliente.id" ORDER BY "custo_total" desc;