/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.cams7.casa_das_quentinhas.entity.Pedido.DestinoOperacao;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.FormaPagamento;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.Situacao;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoAtendimento;
import br.com.cams7.casa_das_quentinhas.entity.Pedido.TipoCliente;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Pedido.class)
public class Pedido_ {
	public static volatile SingularAttribute<Pedido, Long> id;
	public static volatile SingularAttribute<Pedido, Usuario> usuarioCadastro;
	public static volatile SingularAttribute<Pedido, TipoCliente> tipoCliente;
	public static volatile SingularAttribute<Pedido, Short> quantidade;
	public static volatile SingularAttribute<Pedido, Float> custo;
	public static volatile SingularAttribute<Pedido, Float> custoIcms;
	public static volatile SingularAttribute<Pedido, Float> custoSt;
	public static volatile SingularAttribute<Pedido, FormaPagamento> formaPagamento;
	public static volatile SingularAttribute<Pedido, Boolean> consumidorFinal;
	public static volatile SingularAttribute<Pedido, DestinoOperacao> destinoOperacao;
	public static volatile SingularAttribute<Pedido, TipoAtendimento> tipoAtendimento;
	public static volatile SingularAttribute<Pedido, String> naturezaOperacao;
	public static volatile SingularAttribute<Pedido, Situacao> situacao;
	public static volatile SingularAttribute<Pedido, Manutencao> manutencao;
	public static volatile SingularAttribute<Pedido, Empresa> empresa;
	public static volatile SingularAttribute<Pedido, Cliente> cliente;
	public static volatile ListAttribute<Pedido, PedidoItem> itens;
}
