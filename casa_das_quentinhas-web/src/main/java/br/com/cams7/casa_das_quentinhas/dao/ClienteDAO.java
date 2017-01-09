/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao;

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
	 * @param relacao
	 *            Relação entre o cliente e o usuário
	 * @return ID do usuário
	 */
	Integer getUsuarioIdByClienteId(Integer clienteId, Relacao relacao);

	/**
	 * @param nomeOrCpfOrTelefone
	 *            Nome,CPF ou telefone
	 * @return Clientes
	 */
	Map<Integer, String> getClientesByNomeOrCpfOrTelefone(String nomeOrCpfOrTelefone);
}
