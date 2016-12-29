package br.com.cams7.casa_das_quentinhas.dao;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;

public interface FuncionarioDAO extends BaseDAO<Funcionario, Integer> {

	// Set<Funcionario> getFuncionariosByFuncao(Funcao funcao);
	/**
	 * @param id
	 *            ID do funcionário
	 * @return Funcionário
	 */
	Funcionario getFuncionarioById(Integer id);

	/**
	 * @param cpf
	 *            CPF do funcionário
	 * @return ID do funcionário
	 */
	Integer getFuncionarioIdByCpf(String cpf);

	// Funcao getFuncionarioFuncaoById(Integer id);

	// Set<Funcionario> getFuncionariosByUsuarioId(Integer usuarioId);
}
