package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;


public interface UserProfileService {

	AutorizacaoEntity findById(int id);

	AutorizacaoEntity findByType(String type);
	
	List<AutorizacaoEntity> findAll();
	
}
