/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Produto;

/**
 * @author César Magalhães
 *
 */
public interface ProdutoDAO extends BaseDAO<Produto, Integer> {

	/**
	 * @param id
	 *            ID do produto
	 * @return Produto
	 */
	Produto getProdutoById(Integer id);

	/**
	 * @param nomeOrCusto
	 *            Nome ou custo
	 * @return Produtos
	 */
	Map<Integer, String> getProdutosByNomeOrCusto(String nomeOrCusto);

}