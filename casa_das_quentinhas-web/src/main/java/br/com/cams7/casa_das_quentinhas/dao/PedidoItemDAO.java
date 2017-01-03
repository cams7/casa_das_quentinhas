/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;

/**
 * @author César Magalhães
 *
 */
public interface PedidoItemDAO extends BaseDAO<PedidoItem, PedidoItemPK> {

	/**
	 * Remove todos os itens de pedido
	 * 
	 * @param pedidoId
	 *            ID do pedido
	 */
	int deleteByPedido(Long pedidoId);
}
