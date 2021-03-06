/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.AppNotFoundException;
import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoItemDAO;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItem;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItemPK;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class PedidoItemServiceImpl extends AbstractService<PedidoItemPK, PedidoItem, PedidoItemDAO>
		implements PedidoItemService {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoItemDAO#getItemById(java.lang.
	 * Long, java.lang.Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public PedidoItem getItemById(Long pedidoId, Integer produtoId) {
		return getDao().getItemById(pedidoId, produtoId);
	}

}
