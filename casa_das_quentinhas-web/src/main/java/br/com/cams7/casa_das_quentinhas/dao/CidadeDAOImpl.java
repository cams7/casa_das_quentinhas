/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.Cidade;
import br.com.cams7.casa_das_quentinhas.model.Cidade_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class CidadeDAOImpl extends AbstractDAO<Cidade, Integer> implements CidadeDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.CidadeDAO#getCidadesByNomeOrIbge(
	 * java.lang.String)
	 */
	@Override
	public Set<Cidade> getCidadesByNomeOrIbge(String nomeOrIbge) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Cidade> cq = cb.createQuery(ENTITY_TYPE);

		Root<Cidade> from = cq.from(ENTITY_TYPE);

		cq.select(from);

		nomeOrIbge = "%" + nomeOrIbge.toLowerCase() + "%";

		cq.where(cb.or(cb.like(cb.lower(from.get(Cidade_.nome)), nomeOrIbge),
				cb.like(from.get(Cidade_.codigoIbge).as(String.class), nomeOrIbge)));

		cq.orderBy(cb.asc(from.get(Cidade_.nome)));

		TypedQuery<Cidade> tq = getEntityManager().createQuery(cq);
		tq.setMaxResults(5);

		Set<Cidade> empresas = tq.getResultList().stream().collect(Collectors.toSet());

		return empresas;
	}

}
