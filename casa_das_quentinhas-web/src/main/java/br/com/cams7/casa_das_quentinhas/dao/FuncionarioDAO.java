package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Set;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;

public interface FuncionarioDAO extends BaseDAO<Funcionario, Integer> {

	Funcionario getFuncionarioByFuncao(Funcao funcao);

	Set<Funcionario> getFuncionariosByUsuarioId(Integer usuarioId);
}
