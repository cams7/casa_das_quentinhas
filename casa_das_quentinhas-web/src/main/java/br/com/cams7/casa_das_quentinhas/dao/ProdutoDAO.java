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
	 * @param produtoId
	 *            ID do produto
	 * @return ID do usuário
	 */
	Integer getUsuarioIdByProdutoId(Integer produtoId);

	/**
	 * @param produtoId
	 *            ID do produto
	 * @return Array contendo o id do usuário e data de cadastro do produto
	 */
	Object[] getUsuarioIdAndProdutoCadastroByProdutoId(Integer produtoId);

	/**
	 * @param nomeOrCusto
	 *            Nome ou custo
	 * @return Produtos
	 */
	Map<Integer, String> getProdutosByNomeOrCusto(String nomeOrCusto);

}
