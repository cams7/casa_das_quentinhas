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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.entity.Cidade;
import br.com.cams7.casa_das_quentinhas.entity.Cidade_;
import br.com.cams7.casa_das_quentinhas.entity.Estado;
import br.com.cams7.casa_das_quentinhas.entity.Estado_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class CidadeDAOImpl extends AbstractDAO<Integer, Cidade> implements CidadeDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@Override
	protected List<From<?, ?>> getFetchJoins(Root<Cidade> from) {
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
	protected List<From<?, ?>> getJoins(Root<Cidade> from) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.CidadeDAO#getCidadesByNomeOrIbge(
	 * java.lang.String)
	 */
	@Override
	public Map<Integer, String> getCidadesByNomeOrIbge(String nomeOrIbge) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Cidade> from = cq.from(ENTITY_TYPE);
		Join<Cidade, Estado> join = (Join<Cidade, Estado>) from.join(Cidade_.estado, JoinType.LEFT);

		cq.select(cb.array(from.get(Cidade_.id), from.get(Cidade_.nome), join.get(Estado_.sigla)));

		nomeOrIbge = "%" + nomeOrIbge.toLowerCase() + "%";

		cq.where(cb.or(cb.like(cb.lower(from.get(Cidade_.nome)), nomeOrIbge),
				cb.like(from.get(Cidade_.codigoIbge).as(String.class), nomeOrIbge)));

		cq.orderBy(cb.asc(from.get(Cidade_.nome)));

		TypedQuery<Object[]> tq = getEntityManager().createQuery(cq);
		tq.setMaxResults(5);

		Map<Integer, String> cidades = tq.getResultList().stream()
				.collect(Collectors.toMap(cidade -> (Integer) cidade[0],
						cidade -> Cidade.getNomeWithEstadoSigla((String) cidade[1], (String) cidade[2])));

		return cidades;
	}

}
