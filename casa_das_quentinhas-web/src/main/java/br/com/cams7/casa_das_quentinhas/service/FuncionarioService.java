package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;

public interface FuncionarioService extends BaseService<Funcionario, Integer>, FuncionarioDAO {

	/**
	 * @param id
	 *            ID do funcionário
	 * @param cpf
	 *            CPF do funcionário
	 * @return Verifica se o CPF não foi cadastrado anteriormente
	 */
	boolean isCPFUnique(Integer id, String cpf);

}
