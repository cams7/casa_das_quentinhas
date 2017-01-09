/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import static br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao.ACESSO;
import static br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao.CADASTRO;
import static br.com.cams7.casa_das_quentinhas.model.Usuario.Tipo.CLIENTE;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.app.utils.AppInvalidDataException;
import br.com.cams7.app.utils.AppNotFoundException;
import br.com.cams7.casa_das_quentinhas.dao.ClienteDAO;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Manutencao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class ClienteServiceImpl extends AbstractService<ClienteDAO, Cliente, Integer> implements ClienteService {

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
	public void persist(Cliente cliente) {
		Usuario usuario = cliente.getUsuarioAcesso();

		usuario.setEmail(cliente.getContato().getEmail());
		usuario.setTipo(CLIENTE);
		usuarioService.persist(usuario);

		Integer usuarioId = usuarioService.getUsuarioIdByEmail(getUsername());
		cliente.setUsuarioCadastro(new Usuario(usuarioId));

		Manutencao manutencao = new Manutencao();
		manutencao.setCadastro(new Date());
		manutencao.setAlteracao(new Date());

		cliente.setManutencao(manutencao);

		super.persist(cliente);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#update(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void update(Cliente cliente) {
		if (cliente.getId() == null)
			throw new AppInvalidDataException("O cliente não foi informado...");

		if (cliente.getManutencao().getCadastro() == null)
			throw new AppInvalidDataException("A data de cadastro não foi informada...");

		Integer usuarioId = getUsuarioIdByClienteId(cliente.getId(), CADASTRO);
		cliente.setUsuarioCadastro(new Usuario(usuarioId));

		try {
			usuarioId = getUsuarioIdByClienteId(cliente.getId(), ACESSO);
			usuarioService.updateEmail(usuarioId, cliente.getContato().getEmail());

			cliente.getUsuarioAcesso().setId(usuarioId);
		} catch (AppNotFoundException e) {
			cliente.setUsuarioAcesso(null);
		}

		cliente.getManutencao().setAlteracao(new Date());

		super.update(cliente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Integer id) {
		Integer usuarioId = getUsuarioIdByClienteId(id, ACESSO);

		super.delete(id);

		usuarioService.delete(usuarioId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteById(java.lang.
	 * Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Cliente getClienteById(Integer id) {
		return getDao().getClienteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteIdByCpf(java.
	 * lang.String)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getClienteIdByCpf(String cpf) {
		return getDao().getClienteIdByCpf(cpf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteIdByEmail(java.
	 * lang.String)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getClienteIdByEmail(String email) {
		return getDao().getClienteIdByEmail(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#
	 * getUsuarioAcessoIdByClienteId(java.lang.Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getUsuarioIdByClienteId(Integer clienteId, Relacao relacao) {
		return getDao().getUsuarioIdByClienteId(clienteId, relacao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#
	 * getClientesByNomeOrCpfOrTelefone(java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public Map<Integer, String> getClientesByNomeOrCpfOrTelefone(String nomeOrCpfOrTelefone) {
		return getDao().getClientesByNomeOrCpfOrTelefone(nomeOrCpfOrTelefone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.ClienteService#isCPFUnique(java.
	 * lang.Integer, java.lang.String)
	 */
	@Override
	public boolean isCPFUnique(Integer id, String cpf) {
		if (cpf == null || cpf.isEmpty())
			return true;

		try {
			Integer clienteId = getClienteIdByCpf(cpf);

			boolean isUnique = id != null && clienteId.equals(id);
			return isUnique;
		} catch (AppNotFoundException e) {
			LOGGER.info(e.getMessage());
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.ClienteService#isEmailUnique(
	 * java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean isEmailUnique(Integer clienteId, Integer usuarioId, String email) {
		if (email == null || email.isEmpty())
			return true;

		try {
			Integer id = getClienteIdByEmail(email);

			boolean isUnique = clienteId != null && id.equals(clienteId);
			return isUnique;
		} catch (AppNotFoundException e) {
			LOGGER.info(e.getMessage());
		}

		boolean isUnique = usuarioService.isEmailUnique(usuarioId, email);
		return isUnique;
	}

}
