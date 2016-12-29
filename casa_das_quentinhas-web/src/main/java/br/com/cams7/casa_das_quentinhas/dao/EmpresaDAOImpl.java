/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;
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
	public Map<Integer, String> getEmpresasByRazaoSocialOrCnpj(String razaoSocialOrCnpj) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Empresa> from = cq.from(ENTITY_TYPE);

		cq.select(cb.array(from.get(Empresa_.id), from.get(Empresa_.razaoSocial), from.get(Empresa_.cnpj)));

		razaoSocialOrCnpj = "%" + razaoSocialOrCnpj.toLowerCase() + "%";

		cq.where(cb.or(cb.like(cb.lower(from.get(Empresa_.razaoSocial)), razaoSocialOrCnpj),
				cb.like(from.get(Empresa_.cnpj), razaoSocialOrCnpj)));

		cq.orderBy(cb.asc(from.get(Empresa_.razaoSocial)));

		TypedQuery<Object[]> tq = getEntityManager().createQuery(cq);
		tq.setMaxResults(5);

		Map<Integer, String> empresas = tq.getResultList().stream().collect(Collectors.toMap(empresa -> (Integer) empresa[0],
				empresa -> Empresa.getRazaoSocialWithCnpj((String) empresa[1], (String) empresa[2])));

		return empresas;
	}

}
