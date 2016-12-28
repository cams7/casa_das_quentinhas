/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.CidadeDAO;
import br.com.cams7.casa_das_quentinhas.model.Cidade;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class CidadeServiceImpl extends AbstractService<CidadeDAO, Cidade, Integer> implements CidadeService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.CidadeDAO#getCidadesByNomeOrIbge(
	 * java.lang.String)
	 */
	@Override
	public Set<Cidade> getCidadesByNomeOrIbge(String nomeOrIbge) {
		Set<Cidade> cidades = getDao().getCidadesByNomeOrIbge(nomeOrIbge);
		return cidades;
	}

}
