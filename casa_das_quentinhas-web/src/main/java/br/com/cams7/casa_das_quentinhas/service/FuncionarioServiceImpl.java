package br.com.cams7.casa_das_quentinhas.service;

import static br.com.cams7.casa_das_quentinhas.model.Usuario.Tipo.FUNCIONARIO;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.app.utils.AppNotFoundException;
import br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.model.Manutencao;
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
	 * @see
	 * br.com.cams7.app.service.AbstractService#persist(br.com.cams7.app.model.
	 * AbstractEntity, java.lang.String)
	 */
	@Override
	public void persist(Funcionario funcionario) {
		Usuario usuario = funcionario.getUsuario();

		usuario.setTipo(FUNCIONARIO);
		usuarioService.persist(usuario);

		funcionario.setId(usuario.getId());

		usuario = new Usuario(usuarioService.getUsuarioIdByEmail(getUsername()));
		funcionario.setUsuarioCadastro(usuario);

		Manutencao manutencao = new Manutencao();
		manutencao.setCadastro(new Date());
		manutencao.setAlteracao(new Date());

		funcionario.setManutencao(manutencao);

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

		funcionario.getManutencao().setAlteracao(new Date());

		super.update(funcionario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Integer id) {
		super.delete(id);

		usuarioService.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioByIdAndFuncoes(java.lang.Integer,
	 * br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao[])
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Funcionario getFuncionarioByIdAndFuncoes(Integer id, Funcao... funcoes) {
		return getDao().getFuncionarioByIdAndFuncoes(id, funcoes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#getFuncionarioByCpf(
	 * java.lang.String)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getFuncionarioIdByCpf(String cpf) {
		return getDao().getFuncionarioIdByCpf(cpf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#
	 * getFuncionarioFuncaoById(java.lang.Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Funcao getFuncionarioFuncaoById(Integer id) {
		return getDao().getFuncionarioFuncaoById(id);
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

		try {
			Integer funcionarioId = getFuncionarioIdByCpf(cpf);

			boolean isUnique = id != null && funcionarioId.equals(id);
			return isUnique;
		} catch (AppNotFoundException e) {
			LOGGER.info(e.getMessage());
		}

		return true;
	}

}
