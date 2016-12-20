package br.com.cams7.casa_das_quentinhas.repository;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;

public interface AutorizacaoRepository {

	List<AutorizacaoEntity> findAll();

	AutorizacaoEntity findByPapel(String papel);

	AutorizacaoEntity findById(Integer id);
}
