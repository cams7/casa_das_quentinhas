/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.PedidoDAO;
import br.com.cams7.casa_das_quentinhas.model.Pedido;

/**
 * @author César Magalhães
 *
 */
public interface PedidoService extends BaseService<Pedido, Long>, PedidoDAO {

}
