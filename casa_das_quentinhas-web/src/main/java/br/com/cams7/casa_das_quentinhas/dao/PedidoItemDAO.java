/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItem;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItemPK;

/**
 * @author César Magalhães
 *
 */
public interface PedidoItemDAO extends BaseDAO<PedidoItemPK, PedidoItem> {

	/**
	 * @param pedidoId
	 *            ID do pedido
	 * @param produtoId
	 *            ID do produto
	 * @return Item do pedido
	 */
	PedidoItem getItemById(Long pedidoId, Integer produtoId);

	/**
	 * Remove todos os itens de pedido
	 * 
	 * @param pedidoId
	 *            ID do pedido
	 */
	int deleteByPedido(Long pedidoId);
}
