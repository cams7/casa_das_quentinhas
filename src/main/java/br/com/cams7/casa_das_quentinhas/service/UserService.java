package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;


public interface UserService {
	
	UsuarioEntity findById(int id);
	
	UsuarioEntity findBySSO(String sso);
	
	void saveUser(UsuarioEntity user);
	
	void updateUser(UsuarioEntity user);
	
	void deleteUserBySSO(String sso);

	List<UsuarioEntity> findAllUsers(); 
	
	boolean isUserSSOUnique(Integer id, String sso);

}