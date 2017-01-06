package br.com.cams7.casa_das_quentinhas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

@Service
@Transactional
public class UsuarioServiceImpl extends AbstractService<UsuarioDAO, Usuario, Integer> implements UsuarioService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#persist(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void persist(Usuario usuario) {
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenhaCriptografada(senhaCriptografada);

		super.persist(usuario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.service.AbstractService#update(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void update(Usuario usuario) {
		if (!usuario.getSenha().isEmpty())
			usuario.setSenhaCriptografada(passwordEncoder.encode(usuario.getSenha()));
		else
			usuario.setSenhaCriptografada(getUsuarioSenhaById(usuario.getId()));

		super.update(usuario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.UsuarioService#getUsuarioByEmail
	 * (java.lang.String)
	 */
	@Override
	public Usuario getUsuarioByEmail(String email) {
		return getDao().getUsuarioByEmail(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO#getUsuarioIdByEmail(java.
	 * lang.String)
	 */
	@Override
	public Integer getUsuarioIdByEmail(String email) {
		return getDao().getUsuarioIdByEmail(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO#getUsuarioSenhaById(java.
	 * lang.Integer)
	 */
	@Override
	public String getUsuarioSenhaById(Integer id) {
		return getDao().getUsuarioSenhaById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.UsuarioService#isEmailUnique(
	 * java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean isEmailUnique(Integer id, String email) {
		if (email == null || email.isEmpty())
			return true;

		Integer usuarioId = getUsuarioIdByEmail(email);

		if (usuarioId == null)
			return true;

		return id != null && usuarioId == id;
	}

}
