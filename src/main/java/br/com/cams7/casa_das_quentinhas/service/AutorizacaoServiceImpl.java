package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;
import br.com.cams7.casa_das_quentinhas.repository.AutorizacaoRepository;

@Service
@Transactional
public class AutorizacaoServiceImpl implements AutorizacaoService {

	@Autowired
	AutorizacaoRepository repository;

	public AutorizacaoEntity findAutorizacaoById(Integer id) {
		return repository.findById(id);
	}

	public AutorizacaoEntity findAutorizacaoByPapel(String papel) {
		return repository.findByPapel(papel);
	}

	public List<AutorizacaoEntity> findAllAutorizacoes() {
		return repository.findAll();
	}
}
