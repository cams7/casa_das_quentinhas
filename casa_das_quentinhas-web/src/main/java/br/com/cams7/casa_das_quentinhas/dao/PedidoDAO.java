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

	/**
	 * @param pedidoId
	 *            ID do pedido
	 * @return ID do usuário
	 */
	Integer getUsuarioIdByPedidoId(Long pedidoId);

	/**
	 * @param pedidoId
	 *            ID do pedido
	 * @return Array contendo o id do usuário e data de cadastro do pedido
	 */
	Object[] getUsuarioIdAndPedidoCadastroByPedidoId(Long pedidoId);
}
