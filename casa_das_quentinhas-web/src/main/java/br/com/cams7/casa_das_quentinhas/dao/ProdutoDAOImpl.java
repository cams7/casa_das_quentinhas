/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.Produto;
import br.com.cams7.casa_das_quentinhas.model.Produto.Tamanho;
import br.com.cams7.casa_das_quentinhas.model.Produto_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class ProdutoDAOImpl extends AbstractDAO<Produto, Integer> implements ProdutoDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@Override
	protected List<From<?, ?>> getFetchJoins(Root<Produto> from) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getJoins(javax.persistence.criteria.
	 * Root)
	 */
	@Override
	protected List<From<?, ?>> getJoins(Root<Produto> from) {
		return null;
	}

	@Override
	public Produto getProdutoById(Integer id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Produto> cq = cb.createQuery(ENTITY_TYPE);

		Root<Produto> from = cq.from(ENTITY_TYPE);

		from.fetch(Produto_.usuarioCadastro, JoinType.INNER);

		cq.select(from);

		cq.where(cb.equal(from.get(Produto_.id), id));

		TypedQuery<Produto> tq = getEntityManager().createQuery(cq);
		Produto produto = tq.getSingleResult();

		return produto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ProdutoDAO#getProdutosByNomeOrId(
	 * java.lang.String)
	 */
	@Override
	public Map<Integer, String> getProdutosByNomeOrCusto(String nomeOrCusto) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Produto> from = cq.from(ENTITY_TYPE);

		cq.select(cb.array(from.get(Produto_.id), from.get(Produto_.nome), from.get(Produto_.tamanho)));

		nomeOrCusto = "%" + nomeOrCusto.toLowerCase() + "%";

		cq.where(cb.or(cb.like(cb.lower(from.get(Produto_.nome)), nomeOrCusto),
				cb.like(from.get(Produto_.custo).as(String.class), nomeOrCusto)));

		cq.orderBy(cb.asc(from.get(Produto_.nome)));

		TypedQuery<Object[]> tq = getEntityManager().createQuery(cq);
		tq.setMaxResults(5);

		Map<Integer, String> produtos = tq.getResultList().stream()
				.collect(Collectors.toMap(produto -> (Integer) produto[0],
						produto -> Produto.getNomeWithTamanho((String) produto[1], (Tamanho) produto[2])));

		return produtos;
	}

}
