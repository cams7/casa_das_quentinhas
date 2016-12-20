package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;


public interface UsuarioService {
	
	UsuarioEntity findById(Integer id);
	
	UsuarioEntity findByEmail(String email);
	
	void saveUser(UsuarioEntity user);
	
	void updateUser(UsuarioEntity user);
	
	void deleteUserById(Integer id);

	List<UsuarioEntity> findAllUsers(); 
	
	boolean isEmailUnique(Integer id, String email);

}