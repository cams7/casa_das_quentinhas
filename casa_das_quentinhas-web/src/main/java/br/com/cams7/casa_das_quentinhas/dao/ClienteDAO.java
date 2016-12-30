/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Cliente;

/**
 * @author César Magalhães
 *
 */
public interface ClienteDAO extends BaseDAO<Cliente, Integer> {

	/**
	 * @param id
	 *            ID do cliente
	 * @return Cliente
	 */
	Cliente getClienteById(Integer id);

	/**
	 * @param cpf
	 *            CPF do cliente
	 * @return ID do cliente
	 */
	Integer getClienteIdByCpf(String cpf);

	/**
	 * @param email
	 *            E-mail do cliente
	 * @return ID do cliente
	 */
	Integer getClienteIdByEmail(String email);

	/**
	 * @param clienteId
	 *            ID do cliente
	 * @return ID do usuário
	 */
	Integer getUsuarioAcessoIdByClienteId(Integer clienteId);

	/**
	 * @param nomeOrCpfOrTelefone
	 *            Nome,CPF ou telefone
	 * @return Clientes
	 */
	Map<Integer, String> getClientesByNomeOrCpfOrTelefone(String nomeOrCpfOrTelefone);
}
