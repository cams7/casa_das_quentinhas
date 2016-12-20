package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;
import br.com.cams7.casa_das_quentinhas.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioEntity findUsuarioById(Integer id) {
		return repository.findById(id);
	}

	public UsuarioEntity findUsuarioByEmail(String email) {
		UsuarioEntity usuario = repository.findByEmail(email);
		return usuario;
	}

	public void saveUsuario(UsuarioEntity usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		repository.save(usuario);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	public void updateUsuario(UsuarioEntity usuario) {
		UsuarioEntity entity = repository.findById(usuario.getId());
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
		repository.deleteById(id);
	}

	public List<UsuarioEntity> findAllUsuarios() {
		return repository.findAll();
	}

	public boolean isEmailUnique(Integer id, String email) {
		UsuarioEntity usuario = findUsuarioByEmail(email);
		return (usuario == null || ((id != null) && (usuario.getId() == id)));
	}

}
