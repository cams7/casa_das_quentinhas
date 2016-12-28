/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Set;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Cidade;

/**
 * @author César Magalhães
 *
 */
public interface CidadeDAO extends BaseDAO<Cidade, Integer> {

	Set<Cidade> getCidadesByNomeOrIbge(String nomeOrIbge);
}
