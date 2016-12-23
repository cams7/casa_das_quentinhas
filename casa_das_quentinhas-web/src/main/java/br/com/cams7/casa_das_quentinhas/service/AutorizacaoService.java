package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.AutorizacaoDAO;
import br.com.cams7.casa_das_quentinhas.model.Autorizacao;

public interface AutorizacaoService extends BaseService<Autorizacao, Integer>, AutorizacaoDAO {

}
