package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.model.Autorizacao;


public interface AutorizacaoService {

	Autorizacao findAutorizacaoById(Integer id);

	Autorizacao findAutorizacaoByPapel(String papel);
	
	List<Autorizacao> findAllAutorizacoes();
	
}
