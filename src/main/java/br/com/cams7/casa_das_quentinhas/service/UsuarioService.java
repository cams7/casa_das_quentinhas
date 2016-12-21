package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.model.Usuario;

public interface UsuarioService {

	Usuario findUsuarioById(Integer id);

	Usuario findUsuarioByEmail(String email);

	void saveUsuario(Usuario usuario);

	void updateUsuario(Usuario usuario);

	void deleteUsuarioById(Integer id);

	List<Usuario> findAllUsuarios();

	boolean isEmailUnique(Integer id, String email);

}