package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.model.Autorizacao;

public interface AutorizacaoDAO {

	List<Autorizacao> findAll();

	Autorizacao findByPapel(String papel);

	Autorizacao findById(Integer id);
}
