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
	private UsuarioRepository dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioEntity findById(Integer id) {
		return dao.findById(id);
	}

	public UsuarioEntity findByEmail(String email) {
		UsuarioEntity user = dao.findByEmail(email);
		return user;
	}

	public void saveUser(UsuarioEntity user) {
		user.setSenha(passwordEncoder.encode(user.getSenha()));
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	public void updateUser(UsuarioEntity user) {
		UsuarioEntity entity = dao.findById(user.getId());
		if (entity != null) {
			// entity.setSsoId(user.getSsoId());
			if (!user.getSenha().equals(entity.getSenha())) {
				entity.setSenha(passwordEncoder.encode(user.getSenha()));
			}
			entity.setNome(user.getNome());
			entity.setSobrenome(user.getSobrenome());
			entity.setEmail(user.getEmail());
			entity.setAutorizacoes(user.getAutorizacoes());
		}
	}

	public void deleteUserById(Integer id) {
		dao.deleteById(id);
	}

	public List<UsuarioEntity> findAllUsers() {
		return dao.findAllUsers();
	}

	public boolean isEmailUnique(Integer id, String email) {
		UsuarioEntity user = findByEmail(email);
		return (user == null || ((id != null) && (user.getId() == id)));
	}

}
