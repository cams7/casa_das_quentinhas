/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.CidadeDAO;
import br.com.cams7.casa_das_quentinhas.entity.Cidade;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class CidadeServiceImpl extends AbstractService<Integer, Cidade, CidadeDAO> implements CidadeService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.CidadeDAO#getCidadesByNomeOrIbge(
	 * java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public Map<Integer, String> getCidadesByNomeOrIbge(String nomeOrIbge) {
		return getDao().getCidadesByNomeOrIbge(nomeOrIbge);
	}

}
