package br.com.cams7.casa_das_quentinhas.repository;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;

public interface AutorizacaoRepository {

	List<AutorizacaoEntity> findAll();

	AutorizacaoEntity findByType(String type);

	AutorizacaoEntity findById(int id);
}
