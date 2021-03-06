/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoItemDAO;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItem;
import br.com.cams7.casa_das_quentinhas.entity.PedidoItemPK;

/**
 * @author César Magalhães
 *
 */
public interface PedidoItemService extends BaseService<PedidoItemPK, PedidoItem>, PedidoItemDAO {

}
