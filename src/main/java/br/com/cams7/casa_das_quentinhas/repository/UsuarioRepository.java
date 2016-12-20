package br.com.cams7.casa_das_quentinhas.repository;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;

public interface UsuarioRepository {

	UsuarioEntity findById(Integer id);

	UsuarioEntity findByEmail(String email);

	void save(UsuarioEntity user);

	void deleteById(Integer id);

	List<UsuarioEntity> findAllUsers();

}
