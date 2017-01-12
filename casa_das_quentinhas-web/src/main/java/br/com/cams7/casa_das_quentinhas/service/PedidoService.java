/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoDAO;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;

/**
 * @author César Magalhães
 *
 */
public interface PedidoService extends BaseService<Long, Pedido>, PedidoDAO {

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
	 * @param removedItens
	 *            Itens removidos
	 */
	void update(Pedido pedido, List<PedidoItem> itens, List<PedidoItemPK> removedItens);

}
