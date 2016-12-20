package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;

public interface UserDao {

	UsuarioEntity findById(int id);

	UsuarioEntity findBySSO(String sso);

	void save(UsuarioEntity user);

	void deleteBySSO(String sso);

	List<UsuarioEntity> findAllUsers();

}
