package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.model.Usuario;

public interface UsuarioDAO {

	Usuario findById(Integer id);

	Usuario findByEmail(String email);

	void save(Usuario usuario);

	void deleteById(Integer id);

	List<Usuario> findAll();

	List<Usuario> list(Integer offset, Integer maxResults);

	Long count();

}
