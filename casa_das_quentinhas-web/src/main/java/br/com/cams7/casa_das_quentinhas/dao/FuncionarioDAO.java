package br.com.cams7.casa_das_quentinhas.dao;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;

public interface FuncionarioDAO extends BaseDAO<Funcionario, Integer> {

	/**
	 * @param id
	 *            ID do funcionário
	 * @param funcoes
	 *            Funções do funcionário
	 * @return Funcionário
	 */
	Funcionario getFuncionarioByIdAndFuncoes(Integer id, Funcao... funcoes);

	/**
	 * @param cpf
	 *            CPF do funcionário
	 * @return ID do funcionário
	 */
	Integer getFuncionarioIdByCpf(String cpf);

	/**
	 * @param id
	 *            ID do funcionário
	 * @return Função do funcionário
	 */
	Funcao getFuncionarioFuncaoById(Integer id);

	/**
	 * @param funcionarioId
	 *            ID do usuário ou do funcionário
	 * @return ID do usuário
	 */
	Integer getUsuarioCadastroIdByFuncionarioId(Integer funcionarioId);
}
