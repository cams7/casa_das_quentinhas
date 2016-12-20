package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;

public interface UserProfileDao {

	List<AutorizacaoEntity> findAll();

	AutorizacaoEntity findByType(String type);

	AutorizacaoEntity findById(int id);
}
