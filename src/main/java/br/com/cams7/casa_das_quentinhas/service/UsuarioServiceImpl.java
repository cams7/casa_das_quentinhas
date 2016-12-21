package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDAO dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Usuario findUsuarioById(Integer id) {
		return dao.findById(id);
	}

	public Usuario findUsuarioByEmail(String email) {
		Usuario usuario = dao.findByEmail(email);
		return usuario;
	}

	public void saveUsuario(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		dao.save(usuario);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	public void updateUsuario(Usuario usuario) {
		Usuario entity = dao.findById(usuario.getId());
		if (entity != null) {
			// entity.setSsoId(user.getSsoId());
			if (!usuario.getSenha().equals(entity.getSenha())) {
				entity.setSenha(passwordEncoder.encode(usuario.getSenha()));
			}
			entity.setNome(usuario.getNome());
			entity.setSobrenome(usuario.getSobrenome());
			entity.setEmail(usuario.getEmail());
			entity.setAutorizacoes(usuario.getAutorizacoes());
		}
	}

	public void deleteUsuarioById(Integer id) {
		dao.deleteById(id);
	}

	public List<Usuario> findAllUsuarios() {
		return dao.findAll();
	}

	public boolean isEmailUnique(Integer id, String email) {
		Usuario usuario = findUsuarioByEmail(email);
		return (usuario == null || ((id != null) && (usuario.getId() == id)));
	}

}
