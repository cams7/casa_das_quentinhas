package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.dao.AutorizacaoDAO;
import br.com.cams7.casa_das_quentinhas.model.Autorizacao;

@Service
@Transactional
public class AutorizacaoServiceImpl implements AutorizacaoService {

	@Autowired
	AutorizacaoDAO dao;

	public Autorizacao findAutorizacaoById(Integer id) {
		return dao.findById(id);
	}

	public Autorizacao findAutorizacaoByPapel(String papel) {
		return dao.findByPapel(papel);
	}

	public List<Autorizacao> findAllAutorizacoes() {
		return dao.findAll();
	}
}
