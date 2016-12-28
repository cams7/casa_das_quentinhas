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
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class EmpresaDAOImpl extends AbstractDAO<Empresa, Integer> implements EmpresaDAO {

	@Override
	public Set<Empresa> getEmpresasByNomeOrCnpj(String nomeOrCnpj) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Empresa> cq = cb.createQuery(ENTITY_TYPE);

		Root<Empresa> from = cq.from(ENTITY_TYPE);

		cq.select(from);

		nomeOrCnpj = "%" + nomeOrCnpj.toLowerCase() + "%";

		cq.where(cb.or(cb.like(cb.lower(from.get(Empresa_.nome)), nomeOrCnpj),
				cb.like(from.get(Empresa_.cnpj), nomeOrCnpj)));

		cq.orderBy(cb.asc(from.get(Empresa_.nome)));

		TypedQuery<Empresa> tq = getEntityManager().createQuery(cq);
		tq.setMaxResults(5);

		Set<Empresa> empresas = tq.getResultList().stream().collect(Collectors.toSet());

		return empresas;
	}

}
