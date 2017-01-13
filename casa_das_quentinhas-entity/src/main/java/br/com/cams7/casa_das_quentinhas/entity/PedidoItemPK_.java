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
@StaticMetamodel(PedidoItemPK.class)
public class PedidoItemPK_ {
	public static volatile SingularAttribute<PedidoItemPK, Long> pedidoId;
	public static volatile SingularAttribute<PedidoItemPK, Integer> produtoId;
}
