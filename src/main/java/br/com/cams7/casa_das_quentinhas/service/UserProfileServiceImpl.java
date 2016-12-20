package br.com.cams7.casa_das_quentinhas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.entity.AutorizacaoEntity;
import br.com.cams7.casa_das_quentinhas.repository.AutorizacaoRepository;


@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService{
	
	@Autowired
	AutorizacaoRepository dao;
	
	public AutorizacaoEntity findById(int id) {
		return dao.findById(id);
	}

	public AutorizacaoEntity findByType(String type){
		return dao.findByType(type);
	}

	public List<AutorizacaoEntity> findAll() {
		return dao.findAll();
	}
}
