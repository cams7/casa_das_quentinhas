/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.ClienteDAO;
import br.com.cams7.casa_das_quentinhas.model.Cliente;

/**
 * @author César Magalhães
 *
 */
public interface ClienteService extends BaseService<Cliente, Integer>, ClienteDAO {
	/**
	 * @param id
	 *            ID do cliente
	 * @param cpf
	 *            CPF do cliente
	 * @return Verifica se o CPF não foi cadastrado anteriormente
	 */
	boolean isCPFUnique(Integer id, String cpf);

	/**
	 * @param clienteId
	 *            ID do cliente
	 * @param usuarioId
	 *            ID do usuário
	 * @param email
	 *            E-mail do usuário ou do cliente
	 * @return Verifica se o e-mail não foi cadastrado anteriormente
	 */
	boolean isEmailUnique(Integer clienteId, Integer usuarioId, String email);
}
