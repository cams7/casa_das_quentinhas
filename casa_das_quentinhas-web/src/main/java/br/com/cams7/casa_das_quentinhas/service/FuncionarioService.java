package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao;

/**
 * @author César Magalhães
 *
 */
public interface FuncionarioService extends BaseService<Integer, Funcionario>, FuncionarioDAO {

	/**
	 * Salva os dados do funcionário
	 * 
	 * @param funcionario
	 *            Funcionário
	 * @param funcao
	 *            Função do funcionário
	 */
	/**
	 * Salva os dados do funcionário
	 * 
	 * @param funcionario
	 *            Funcionário
	 * @param possiveisFuncoes
	 *            Posiveis funções para o funcionário
	 */
	void persist(Funcionario funcionario, Funcao... possiveisFuncoes);

	/**
	 * Atualiza os dados do funcionário
	 * 
	 * @param funcionario
	 *            Funcionário
	 * @param possiveisFuncoes
	 *            Posiveis funções para o funcionário
	 */
	void update(Funcionario funcionario, Funcao... possiveisFuncoes);

	/**
	 * Remove os dados do funcionário
	 * 
	 * @param id
	 *            ID do funcionário
	 * @param possiveisFuncoes
	 *            Posiveis funções para o funcionário
	 */
	void delete(Integer id, Funcao... possiveisFuncoes);

	/**
	 * @param id
	 *            ID do funcionário
	 * @param cpf
	 *            CPF do funcionário
	 * @return Verifica se o CPF não foi cadastrado anteriormente
	 */
	boolean isCPFUnique(Integer id, String cpf);

}
