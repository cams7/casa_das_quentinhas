package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao;

/**
 * @author César Magalhães
 *
 */
public interface FuncionarioDAO extends BaseDAO<Integer, Funcionario> {

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
	 *            ID do funcionário (id do usuário de acesso)
	 * @return ID do usuário de cadastro
	 */
	Integer getUsuarioIdByFuncionarioId(Integer funcionarioId);

	/**
	 * @param funcionarioId
	 *            ID do funcionário (id do usuário de acesso)
	 * @return Array contendo o id do usuário de cadastro e data de cadastro do
	 *         funcionário
	 */
	Object[] getUsuarioIdAndFuncionarioCadastroByFuncionarioId(Integer funcionarioId);

	/**
	 * @param nomeOrCpfOrCelular
	 *            Nome,CPF ou celular
	 * @return Entregadores
	 */
	Map<Integer, String> getEntregadoresByNomeOrCpfOrCelular(String nomeOrCpfOrCelular);
}
