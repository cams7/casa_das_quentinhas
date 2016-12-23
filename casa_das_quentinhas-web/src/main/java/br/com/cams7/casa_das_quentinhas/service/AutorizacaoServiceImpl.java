package br.com.cams7.casa_das_quentinhas.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.AutorizacaoDAO;
import br.com.cams7.casa_das_quentinhas.model.Autorizacao;

@Service
@Transactional
public class AutorizacaoServiceImpl extends AbstractService<AutorizacaoDAO, Autorizacao, Integer>
		implements AutorizacaoService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.AutorizacaoDAO#getAutorizacaoByPapel
	 * (java.lang.String)
	 */
	@Override
	public Autorizacao getAutorizacaoByPapel(String papel) {
		Autorizacao autorizacao = getDao().getAutorizacaoByPapel(papel);
		return autorizacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.AutorizacaoDAO#
	 * getAutorizacoesByUsuarioId(java.lang.Integer)
	 */
	@Override
	public Set<Autorizacao> getAutorizacoesByUsuarioId(Integer usuarioId) {
		Set<Autorizacao> autorizacoes = getDao().getAutorizacoesByUsuarioId(usuarioId);
		return autorizacoes;
	}

}
