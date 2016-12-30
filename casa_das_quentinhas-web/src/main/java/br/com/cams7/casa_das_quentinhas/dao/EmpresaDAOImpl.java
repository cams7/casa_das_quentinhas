/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.Cidade;
import br.com.cams7.casa_das_quentinhas.model.Cidade_;
import br.com.cams7.casa_das_quentinhas.model.Contato_;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa_;
import br.com.cams7.casa_das_quentinhas.model.Estado;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

/**
 * @author César Magalhães
 *
 */
@Repository
public class EmpresaDAOImpl extends AbstractDAO<Empresa, Integer> implements EmpresaDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFromWithFetchJoins(javax.persistence.
	 * criteria.Root)
	 */
	@Override
	protected From<?, ?>[] getFromWithFetchJoins(Root<Empresa> from) {
		@SuppressWarnings("unchecked")
		Join<Empresa, Usuario> joinUsuarioAcesso = (Join<Empresa, Usuario>) from.fetch(Empresa_.usuarioAcesso,
				JoinType.LEFT);
		@SuppressWarnings("unchecked")
		Join<Empresa, Usuario> joinUsuarioCadastro = (Join<Empresa, Usuario>) from.fetch(Empresa_.usuarioCadastro,
				JoinType.LEFT);
		@SuppressWarnings("unchecked")
		Join<Empresa, Cidade> joinCidade = (Join<Empresa, Cidade>) from.fetch(Empresa_.cidade, JoinType.LEFT);
		@SuppressWarnings("unchecked")
		Join<Cidade, Estado> joinEstado = (Join<Cidade, Estado>) joinCidade.fetch(Cidade_.estado, JoinType.LEFT);

		From<?, ?>[] fromWithJoins = new From<?, ?>[] { from, joinUsuarioAcesso, joinUsuarioCadastro, joinCidade,
				joinEstado };

		return fromWithJoins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.AbstractDAO#getFromWithJoins(javax.persistence.
	 * criteria.Root)
	 */
	@Override
	protected From<?, ?>[] getFromWithJoins(Root<Empresa> from) {
		Join<Empresa, Usuario> joinUsuarioAcesso = (Join<Empresa, Usuario>) from.join(Empresa_.usuarioAcesso,
				JoinType.LEFT);
		Join<Empresa, Usuario> joinUsuarioCadastro = (Join<Empresa, Usuario>) from.join(Empresa_.usuarioCadastro,
				JoinType.LEFT);
		Join<Empresa, Cidade> joinCidade = (Join<Empresa, Cidade>) from.join(Empresa_.cidade, JoinType.LEFT);
		Join<Cidade, Estado> joinEstado = (Join<Cidade, Estado>) joinCidade.join(Cidade_.estado, JoinType.LEFT);

		From<?, ?>[] fromWithJoins = new From<?, ?>[] { from, joinUsuarioAcesso, joinUsuarioCadastro, joinCidade,
				joinEstado };

		return fromWithJoins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaById(java.lang.
	 * Integer)
	 */
	@Override
	public Empresa getEmpresaById(Integer id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Empresa> cq = cb.createQuery(ENTITY_TYPE);

		Root<Empresa> from = cq.from(ENTITY_TYPE);

		from.fetch(Empresa_.usuarioAcesso, JoinType.LEFT);
		from.fetch(Empresa_.usuarioCadastro, JoinType.LEFT);
		from.fetch(Empresa_.cidade, JoinType.LEFT).fetch(Cidade_.estado, JoinType.LEFT);

		cq.select(from);

		cq.where(cb.equal(from.get(Empresa_.id), id));

		TypedQuery<Empresa> tq = getEntityManager().createQuery(cq);
		Empresa funcionario = tq.getSingleResult();

		return funcionario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaByCnpj(java.
	 * lang.String)
	 */
	@Override
	public Integer getEmpresaIdByCnpj(String cnpj) {
		LOGGER.info("CNPJ : {}", cnpj);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Empresa> from = cq.from(ENTITY_TYPE);

		cq.select(from.get(Empresa_.id));

		cq.where(cb.equal(from.get(Empresa_.cnpj), cnpj));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer empresaId = tq.getSingleResult();
			return empresaId;
		} catch (NoResultException e) {
			LOGGER.warn("CNPJ not found...");
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaIdByEmail(java.
	 * lang.String)
	 */
	@Override
	public Integer getEmpresaIdByEmail(String email) {
		LOGGER.info("E-mail : {}", email);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Empresa> from = cq.from(ENTITY_TYPE);

		cq.select(from.get(Empresa_.id));

		cq.where(cb.equal(from.get(Empresa_.contato).get(Contato_.email), email));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer empresaId = tq.getSingleResult();
			return empresaId;
		} catch (NoResultException e) {
			LOGGER.warn("E-mail not found...");
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#
	 * getEmpresasByRazaoSocialOrCnpj(java.lang.String)
	 */
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

		Map<Integer, String> empresas = tq.getResultList().stream()
				.collect(Collectors.toMap(empresa -> (Integer) empresa[0],
						empresa -> Empresa.getRazaoSocialWithCnpj((String) empresa[1], (String) empresa[2])));

		return empresas;
	}

}
