/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Pedido;

/**
 * @author César Magalhães
 *
 */
public interface PedidoDAO extends BaseDAO<Pedido, Long> {

	/**
	 * @param id
	 *            ID do pedido
	 * @return Pedido
	 */
	Pedido getPedidoById(Long id);
}
