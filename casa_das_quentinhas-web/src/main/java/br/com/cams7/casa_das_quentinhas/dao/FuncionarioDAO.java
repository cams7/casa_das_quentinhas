package br.com.cams7.casa_das_quentinhas.dao;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;

public interface FuncionarioDAO extends BaseDAO<Funcionario, Integer> {

	// Set<Funcionario> getFuncionariosByFuncao(Funcao funcao);
	Funcionario getFuncionarioById(Integer id);

	Funcao getFuncionarioFuncaoById(Integer id);

	// Set<Funcionario> getFuncionariosByUsuarioId(Integer usuarioId);
}
