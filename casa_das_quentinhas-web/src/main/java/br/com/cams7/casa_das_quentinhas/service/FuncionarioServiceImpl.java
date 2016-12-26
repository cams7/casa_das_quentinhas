package br.com.cams7.casa_das_quentinhas.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;

@Service
@Transactional
public class FuncionarioServiceImpl extends AbstractService<FuncionarioDAO, Funcionario, Integer>
		implements FuncionarioService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioByFuncao(br.com.cams7.casa_das_quentinhas.model.Funcionario
	 * .Funcao)
	 */
	// @Override
	// public Set<Funcionario> getFuncionariosByFuncao(Funcao funcao) {
	// Set<Funcionario> funcionario = getDao().getFuncionariosByFuncao(funcao);
	// return funcionario;
	// }

	@Override
	public Funcionario getFuncionarioById(Integer id) {
		Funcionario funcionario = getDao().getFuncionarioById(id);
		return funcionario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioFuncaoById(java.lang.Integer)
	 */
	@Override
	public Funcao getFuncionarioFuncaoById(Integer id) {
		Funcao funcao = getDao().getFuncionarioFuncaoById(id);
		return funcao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionariosByUsuarioId(java.lang.Integer)
	 */
	// @Override
	// public Set<Funcionario> getFuncionariosByUsuarioId(Integer usuarioId) {
	// Set<Funcionario> funcionarios =
	// getDao().getFuncionariosByUsuarioId(usuarioId);
	// return funcionarios;
	// }

}
