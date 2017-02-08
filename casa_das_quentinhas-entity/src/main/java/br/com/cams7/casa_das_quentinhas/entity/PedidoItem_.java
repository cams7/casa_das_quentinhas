/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author cesar
 *
 */
@StaticMetamodel(PedidoItem.class)
public class PedidoItem_ {
	public static volatile SingularAttribute<PedidoItem, PedidoItemPK> id;
	public static volatile SingularAttribute<PedidoItem, Pedido> pedido;
	public static volatile SingularAttribute<PedidoItem, Produto> produto;
	public static volatile SingularAttribute<PedidoItem, Short> quantidade;
	public static volatile SingularAttribute<PedidoItem, Float> custo;
	public static volatile SingularAttribute<PedidoItem, String> codigoCfop;
	public static volatile SingularAttribute<PedidoItem, String> codigoIcms;
	public static volatile SingularAttribute<PedidoItem, Character> codigoIcmsOrigem;
	public static volatile SingularAttribute<PedidoItem, String> codigoPis;
	public static volatile SingularAttribute<PedidoItem, String> codigoCofins;
	public static volatile SingularAttribute<PedidoItem, String> codigoCsosn;
}
