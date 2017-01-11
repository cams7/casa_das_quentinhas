/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import static br.com.cams7.casa_das_quentinhas.model.Usuario.RelacionamentoUsuario.ACESSO;
import static br.com.cams7.casa_das_quentinhas.model.Usuario.RelacionamentoUsuario.CADASTRO;
import static br.com.cams7.casa_das_quentinhas.model.Usuario.Tipo.EMPRESA;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.app.utils.AppInvalidDataException;
import br.com.cams7.app.utils.AppNotFoundException;
import br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.RelacionamentoEmpresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.model.Manutencao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Usuario.RelacionamentoUsuario;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class EmpresaServiceImpl extends AbstractService<EmpresaDAO, Empresa, Integer> implements EmpresaService {

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
	public void persist(Empresa empresa) {
		Usuario usuario = empresa.getUsuarioAcesso();

		usuario.setEmail(empresa.getContato().getEmail());
		usuario.setTipo(EMPRESA);
		usuarioService.persist(usuario);

		Integer usuarioId = usuarioService.getUsuarioIdByEmail(getUsername());
		empresa.setUsuarioCadastro(new Usuario(usuarioId));

		empresa.setManutencao(new Manutencao(new Date(), new Date()));

		super.persist(empresa);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#update(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void update(Empresa empresa) {
		Integer id = empresa.getId();
		verificaId(id);

		if (id.equals(1))
			throw new AppInvalidDataException(String.format("A empresa (id: %s) não é válida...", id));

		Object[] array = getUsuarioIdAndEmpresaCadastroByEmpresaId(id, CADASTRO);

		Integer usuarioId = (Integer) array[0];
		Date cadastro = (Date) array[1];

		empresa.setUsuarioCadastro(new Usuario(usuarioId));
		empresa.setManutencao(new Manutencao(cadastro, new Date()));

		try {
			usuarioId = getUsuarioIdByEmpresaId(empresa.getId(), ACESSO);

			usuarioService.updateEmailAndSenha(usuarioId, empresa.getContato().getEmail(),
					empresa.getUsuarioAcesso().getSenha());

			empresa.getUsuarioAcesso().setId(usuarioId);
		} catch (AppNotFoundException e) {
			empresa.setUsuarioAcesso(null);
		}

		super.update(empresa);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Integer id) {
		Integer usuarioId = getUsuarioAcessoIdByEmpresaId(id);

		super.delete(id);

		if (usuarioId != null)
			usuarioService.delete(usuarioId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaByIdAndTipos(
	 * java.lang.Integer, br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo[])
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Empresa getEmpresaByIdAndTipos(Integer id, Tipo... tipos) {
		return getDao().getEmpresaByIdAndTipos(id, tipos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaByCnpj(java.
	 * lang.String)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getEmpresaIdByCnpj(String cnpj) {
		return getDao().getEmpresaIdByCnpj(cnpj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaIdByEmail(java.
	 * lang.String)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getEmpresaIdByEmail(String email) {
		return getDao().getEmpresaIdByEmail(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#
	 * getUsuarioAcessoIdByEmpresaId(java.lang.Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getUsuarioAcessoIdByEmpresaId(Integer empresaId) {
		return getDao().getUsuarioAcessoIdByEmpresaId(empresaId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaIipoById(java.
	 * lang.Integer)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Tipo getEmpresaIipoById(Integer id) {
		return getDao().getEmpresaIipoById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getUsuarioIdByEmpresaId(
	 * java.lang.Integer,
	 * br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Integer getUsuarioIdByEmpresaId(Integer empresaId, RelacionamentoUsuario relacao) {
		return getDao().getUsuarioIdByEmpresaId(empresaId, relacao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#
	 * getUsuarioIdAndEmpresaCadastroByEmpresaId(java.lang.Integer,
	 * br.com.cams7.casa_das_quentinhas.model.Usuario.RelacionamentoUsuario)
	 */
	@Transactional(readOnly = true, noRollbackFor = AppNotFoundException.class)
	@Override
	public Object[] getUsuarioIdAndEmpresaCadastroByEmpresaId(Integer empresaId, RelacionamentoUsuario relacionamento) {
		return getDao().getUsuarioIdAndEmpresaCadastroByEmpresaId(empresaId, relacionamento);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#countByEmpresaId(java.
	 * lang.Integer,
	 * br.com.cams7.casa_das_quentinhas.model.Empresa.RelacionamentoEmpresa)
	 */
	@Transactional(readOnly = true)
	@Override
	public long countByEmpresaId(Integer empresaId, RelacionamentoEmpresa relacionamento) {
		return getDao().countByEmpresaId(empresaId, relacionamento);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#
	 * getEmpresasByRazaoSocialOrCnpj(java.lang.String,
	 * br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo)
	 */
	@Transactional(readOnly = true)
	@Override
	public Map<Integer, String> getEmpresasByRazaoSocialOrCnpj(String razaoSocialOrCnpj, Tipo tipo) {
		return getDao().getEmpresasByRazaoSocialOrCnpj(razaoSocialOrCnpj, tipo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.EmpresaService#isCNPJUnique(java
	 * .lang.Integer, java.lang.String)
	 */
	@Override
	public boolean isCNPJUnique(Integer id, String cnpj) {
		if (cnpj == null || cnpj.isEmpty())
			return true;

		try {
			Integer empresaId = getEmpresaIdByCnpj(cnpj);

			boolean isUnique = id != null && empresaId.equals(id);
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
	 * br.com.cams7.casa_das_quentinhas.service.EmpresaService#isEmailUnique(
	 * java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean isEmailUnique(Integer empresaId, Integer usuarioId, String email) {
		if (email == null || email.isEmpty())
			return true;

		try {
			Integer id = getEmpresaIdByEmail(email);

			boolean isUnique = empresaId != null && id.equals(empresaId);
			return isUnique;
		} catch (AppNotFoundException e) {
			LOGGER.info(e.getMessage());
		}

		boolean isUnique = usuarioService.isEmailUnique(usuarioId, email);
		return isUnique;
	}

}
