package br.com.cams7.casa_das_quentinhas.service;

import static br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo.ENTREGA;
import static br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao.GERENTE;
import static br.com.cams7.casa_das_quentinhas.model.Usuario.Tipo.FUNCIONARIO;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.app.utils.AppInvalidDataException;
import br.com.cams7.app.utils.AppNotFoundException;
import br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.model.Manutencao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class FuncionarioServiceImpl extends AbstractService<FuncionarioDAO, Funcionario, Integer>
		implements FuncionarioService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmpresaService empresaService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.FuncionarioService#persist(br.
	 * com.cams7.casa_das_quentinhas.model.Funcionario,
	 * br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao[])
	 */
	@Override
	public void persist(Funcionario funcionario, Funcao... possiveisFuncoes) {
		verificaFuncoes(funcionario.getFuncao(), possiveisFuncoes);
		verificaEmpresa(funcionario.getFuncao(), funcionario.getEmpresa().getId());

		Usuario usuario = funcionario.getUsuario();

		usuario.setTipo(FUNCIONARIO);
		usuarioService.persist(usuario);

		funcionario.setId(usuario.getId());

		Integer usuarioId = usuarioService.getUsuarioIdByEmail(getUsername());
		funcionario.setUsuarioCadastro(new Usuario(usuarioId));

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
	 * br.com.cams7.casa_das_quentinhas.service.FuncionarioService#update(br.com
	 * .cams7.casa_das_quentinhas.model.Funcionario,
	 * br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao[])
	 */
	@Override
	public void update(Funcionario funcionario, Funcao... possiveisFuncoes) {
		verificaIdAndCadastro(funcionario.getId(),
				funcionario.getManutencao() != null ? funcionario.getManutencao().getCadastro() : null);

		verificaFuncoes(funcionario.getFuncao(), possiveisFuncoes);
		verificaEmpresa(funcionario.getFuncao(), funcionario.getEmpresa().getId());

		Integer usuarioId = getUsuarioCadastroIdByFuncionarioId(funcionario.getId());
		funcionario.setUsuarioCadastro(new Usuario(usuarioId));

		Usuario usuario = funcionario.getUsuario();
		usuario.setTipo(FUNCIONARIO);

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
	 * br.com.cams7.casa_das_quentinhas.dao.FuncionarioDAO#getUsuarioIdById(java
	 * .lang.Integer, br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getUsuarioCadastroIdByFuncionarioId(Integer funcionarioId) {
		return getDao().getUsuarioCadastroIdByFuncionarioId(funcionarioId);
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

	/**
	 * Verifica se a função informada é valida
	 * 
	 * @param funcao
	 *            Função do funcionário
	 * @param possiveisFuncoes
	 *            Possíveis funções
	 */
	private void verificaFuncoes(Funcao funcao, Funcao... possiveisFuncoes) {
		if (!Arrays.stream(possiveisFuncoes).anyMatch(f -> f.equals(funcao)))
			throw new AppInvalidDataException(String.format("A função (%s) do funcionário não é válida...", funcao));
	}

	/**
	 * Verifica se a empresa é valida
	 * 
	 * @param funcao
	 *            Função do funcionário
	 * @param empresaId
	 *            ID da empresa
	 */
	private void verificaEmpresa(Funcao funcao, Integer empresaId) {
		String errorMessage = String.format("A empresa (id: %s) não é válida...", empresaId);

		if (empresaId.equals(1) && !Arrays.asList(GERENTE, ATENDENTE).stream().anyMatch(f -> f.equals(funcao)))
			throw new AppInvalidDataException(errorMessage);

		if (!empresaId.equals(1)) {
			Tipo tipo = empresaService.getEmpresaIipoById(empresaId);
			if (!ENTREGA.equals(tipo))
				throw new AppInvalidDataException(errorMessage);
		}
	}

}
