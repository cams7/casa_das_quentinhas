/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoDAO;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;

/**
 * @author César Magalhães
 *
 */
public interface PedidoService extends BaseService<Pedido, Long>, PedidoDAO {

	/**
	 * Salva um pedido
	 * 
	 * @param pedido
	 *            Pedido
	 * @param itens
	 *            Itens do pedido
	 */
	void persist(Pedido pedido, List<PedidoItem> itens);

	/**
	 * Atualiza um pedido
	 * 
	 * @param pedido
	 *            Pedido
	 * @param itens
	 *            Itens do pedido
	 */
	void update(Pedido pedido, List<PedidoItem> itens);

	/**
	 * Remove um item do pedido
	 * 
	 * @param pedidoId
	 *            ID do pedido
	 * @param produtoId
	 *            ID do produto
	 */
	void deleteItem(Long pedidoId, Integer produtoId);

}
