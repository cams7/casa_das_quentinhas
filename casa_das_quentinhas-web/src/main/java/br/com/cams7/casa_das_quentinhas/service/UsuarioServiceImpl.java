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
	 * br.com.cams7.casa_das_quentinhas.service.UsuarioService#getUsuarioById(
	 * java.lang.Integer)
	 */
	// @Override
	// public Usuario getUsuarioById(Integer id) {
	// Usuario usuario = getDao().getUsuarioById(id);
	// return usuario;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.UsuarioService#getUsuarioByEmail
	 * (java.lang.String)
	 */
	@Override
	public Usuario getUsuarioByEmail(String email) {
		Usuario usuario = getDao().getUsuarioByEmail(email);
		return usuario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.service.AbstractService#persist(br.com.
	 * cams7.casa_das_quentinhas.model.AbstractEntity)
	 */
	@Override
	public void persist(Usuario usuario) {
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenhaCriptografada(senhaCriptografada);
		super.persist(usuario);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
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
	 * br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO#getUsuarioSenhaById(java.
	 * lang.Integer)
	 */
	@Override
	public String getUsuarioSenhaById(Integer id) {
		String senhaCriptografada = getDao().getUsuarioSenhaById(id);
		return senhaCriptografada;
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
		Usuario usuario = getUsuarioByEmail(email);

		if (usuario == null)
			return true;

		return id != null && usuario.getId() == id;
	}

}
