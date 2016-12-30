/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import static br.com.cams7.casa_das_quentinhas.model.Usuario.Tipo.CLIENTE;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.ClienteDAO;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

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
	 * @see br.com.cams7.app.dao.BaseDAO#persist(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void persist(Cliente cliente) {
		Usuario usuario = cliente.getUsuarioAcesso();

		usuario.setEmail(cliente.getContato().getEmail());
		usuario.setTipo(CLIENTE);
		usuarioService.persist(usuario);

		cliente.setCadastro(new Date());
		cliente.setAlteracao(new Date());

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
		Usuario usuario = cliente.getUsuarioAcesso();

		if (usuario != null && usuario.getId() != null) {
			usuario.setEmail(cliente.getContato().getEmail());
			usuarioService.update(usuario);
		} else
			cliente.setUsuarioAcesso(null);

		cliente.setAlteracao(new Date());

		super.update(cliente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Integer id) {
		Integer usuarioId = getUsuarioAcessoIdByClienteId(id);

		super.delete(id);

		if (usuarioId != null)
			usuarioService.delete(usuarioId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteById(java.lang.
	 * Integer)
	 */
	@Override
	public Cliente getClienteById(Integer id) {
		Cliente cliente = getDao().getClienteById(id);
		return cliente;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteIdByCpf(java.
	 * lang.String)
	 */
	@Override
	public Integer getClienteIdByCpf(String cpf) {
		Integer clienteId = getDao().getClienteIdByCpf(cpf);
		return clienteId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteIdByEmail(java.
	 * lang.String)
	 */
	@Override
	public Integer getClienteIdByEmail(String email) {
		Integer clienteId = getDao().getClienteIdByEmail(email);
		return clienteId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#
	 * getUsuarioAcessoIdByClienteId(java.lang.Integer)
	 */
	@Override
	public Integer getUsuarioAcessoIdByClienteId(Integer clienteId) {
		Integer usuarioId = getDao().getUsuarioAcessoIdByClienteId(clienteId);
		return usuarioId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#
	 * getClientesByNomeOrCpfOrTelefone(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getClientesByNomeOrCpfOrTelefone(String nomeOrCpfOrTelefone) {
		Map<Integer, String> clientes = getDao().getClientesByNomeOrCpfOrTelefone(nomeOrCpfOrTelefone);
		return clientes;
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

		Integer clienteId = getClienteIdByCpf(cpf);

		if (clienteId == null)
			return true;

		return id != null && clienteId == id;
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

		Integer id = getClienteIdByEmail(email);

		if (id != null && (clienteId == null || !id.equals(clienteId)))
			return false;

		id = usuarioService.getUsuarioIdByEmail(email);

		if (id == null)
			return true;

		return usuarioId != null && id == usuarioId;
	}

}
