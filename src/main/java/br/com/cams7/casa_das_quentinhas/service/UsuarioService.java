package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;

public interface UsuarioService {

	UsuarioEntity findUsuarioById(Integer id);

	UsuarioEntity findUsuarioByEmail(String email);

	void saveUsuario(UsuarioEntity usuario);

	void updateUsuario(UsuarioEntity usuario);

	void deleteUsuarioById(Integer id);

	List<UsuarioEntity> findAllUsuarios();

	boolean isEmailUnique(Integer id, String email);

}