/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoItemDAO;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class PedidoItemServiceImpl extends AbstractService<PedidoItemDAO, PedidoItem, PedidoItemPK>
		implements PedidoItemService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoItemDAO#getItemById(java.lang.
	 * Long, java.lang.Integer)
	 */
	@Override
	public PedidoItem getItemById(Long pedidoId, Integer produtoId) {
		return getDao().getItemById(pedidoId, produtoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoItemDAO#deleteByPedido(java.
	 * lang.Long)
	 */
	@Override
	public int deleteByPedido(Long pedidoId) {
		return getDao().deleteByPedido(pedidoId);
	}

}
