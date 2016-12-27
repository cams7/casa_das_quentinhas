package br.com.cams7.casa_das_quentinhas.service;

import static br.com.cams7.casa_das_quentinhas.model.Usuario.Tipo.FUNCIONARIO;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

@Service
@Transactional
public class FuncionarioServiceImpl extends AbstractService<FuncionarioDAO, Funcionario, Integer>
		implements FuncionarioService {

	@Autowired
	private UsuarioService usuarioService;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#persist(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void persist(Funcionario funcionario) {
		Usuario usuario = funcionario.getUsuario();

		usuario.setTipo(FUNCIONARIO);
		usuarioService.persist(usuario);

		funcionario.setId(usuario.getId());

		funcionario.setCadastro(new Date());
		funcionario.setAlteracao(new Date());

		super.persist(funcionario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#update(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void update(Funcionario funcionario) {
		Usuario usuario = funcionario.getUsuario();
		usuarioService.update(usuario);

		funcionario.setAlteracao(new Date());
		super.update(funcionario);
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
		usuarioService.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#getFuncionarioById(
	 * java.lang.Integer)
	 */
	@Override
	public Funcionario getFuncionarioById(Integer id) {
		Funcionario funcionario = getDao().getFuncionarioById(id);
		return funcionario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#getFuncionarioByCpf(
	 * java.lang.String)
	 */
	@Override
	public Funcionario getFuncionarioByCpf(String cpf) {
		Funcionario funcionario = getDao().getFuncionarioByCpf(cpf);
		return funcionario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.FuncionarioService#isCPFUnique(
	 * java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean isCPFUnique(Integer id, String cpf) {

		if (cpf == null || cpf.isEmpty())
			return true;

		Funcionario funcionario = getFuncionarioByCpf(cpf);

		if (funcionario == null)
			return true;

		return id != null && funcionario.getId() == id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioFuncaoById(java.lang.Integer)
	 */
	// @Override
	// public Funcao getFuncionarioFuncaoById(Integer id) {
	// Funcao funcao = getDao().getFuncionarioFuncaoById(id);
	// return funcao;
	// }

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
