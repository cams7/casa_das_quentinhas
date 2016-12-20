package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;


public interface AutorizacaoService {

	AutorizacaoEntity findById(int id);

	AutorizacaoEntity findByPapel(String papel);
	
	List<AutorizacaoEntity> findAll();
	
}