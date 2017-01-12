/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.entity.Cidade;

/**
 * @author César Magalhães
 *
 */
public interface CidadeDAO extends BaseDAO<Integer, Cidade> {

	/**
	 * @param nomeOrIbge
	 *            Nome da cidade ou o código do IBGE
	 * @return Cidades
	 */
	Map<Integer, String> getCidadesByNomeOrIbge(String nomeOrIbge);
}
