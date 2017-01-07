/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import static br.com.cams7.casa_das_quentinhas.model.Usuario.Tipo.EMPRESA;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.model.Manutencao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

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

		usuario = new Usuario(usuarioService.getUsuarioIdByEmail(getUsername()));
		empresa.setUsuarioCadastro(usuario);

		Manutencao manutencao = new Manutencao();
		manutencao.setCadastro(new Date());
		manutencao.setAlteracao(new Date());

		empresa.setManutencao(manutencao);

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
		Usuario usuario = empresa.getUsuarioAcesso();

		if (usuario != null && usuario.getId() != null) {
			usuario.setEmail(empresa.getContato().getEmail());
			usuarioService.update(usuario);
		} else
			empresa.setUsuarioAcesso(null);

		empresa.getManutencao().setAlteracao(new Date());

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
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaById(java.lang.
	 * Integer)
	 */
	@Override
	public Empresa getEmpresaById(Integer id) {
		return getDao().getEmpresaById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaByCnpj(java.
	 * lang.String)
	 */
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
	@Override
	public Tipo getEmpresaIipoById(Integer id) {
		return getDao().getEmpresaIipoById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#
	 * getEmpresasByRazaoSocialOrCnpj(java.lang.String,
	 * br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo)
	 */
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

		Integer empresaId = getEmpresaIdByCnpj(cnpj);

		if (empresaId == null)
			return true;

		return id != null && empresaId.equals(id);
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

		Integer id = getEmpresaIdByEmail(email);

		if (id != null && (empresaId == null || !id.equals(empresaId)))
			return false;

		id = usuarioService.getUsuarioIdByEmail(email);

		if (id == null)
			return true;

		return usuarioId != null && id.equals(usuarioId);
	}

}
