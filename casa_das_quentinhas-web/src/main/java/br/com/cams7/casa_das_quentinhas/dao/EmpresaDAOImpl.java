/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import static br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao.ACESSO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.app.utils.AppNotFoundException;
import br.com.cams7.casa_das_quentinhas.model.Cidade;
import br.com.cams7.casa_das_quentinhas.model.Cidade_;
import br.com.cams7.casa_das_quentinhas.model.Contato_;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.model.Empresa_;
import br.com.cams7.casa_das_quentinhas.model.Estado;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao;
import br.com.cams7.casa_das_quentinhas.model.Usuario_;

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
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<From<?, ?>> getFetchJoins(Root<Empresa> from) {
		List<From<?, ?>> fetchJoins = new ArrayList<>();

		Join<Empresa, Cidade> joinCidade = (Join<Empresa, Cidade>) from.fetch(Empresa_.cidade, JoinType.INNER);
		Join<Cidade, Estado> joinEstado = (Join<Cidade, Estado>) joinCidade.fetch(Cidade_.estado, JoinType.INNER);

		fetchJoins.add(joinCidade);
		fetchJoins.add(joinEstado);

		return fetchJoins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getJoins(javax.persistence.criteria.
	 * Root)
	 */
	@Override
	protected List<From<?, ?>> getJoins(Root<Empresa> from) {
		List<From<?, ?>> fetchJoins = new ArrayList<>();

		Join<Empresa, Cidade> joinCidade = (Join<Empresa, Cidade>) from.join(Empresa_.cidade, JoinType.INNER);
		Join<Cidade, Estado> joinEstado = (Join<Cidade, Estado>) joinCidade.join(Cidade_.estado, JoinType.INNER);

		fetchJoins.add(joinCidade);
		fetchJoins.add(joinEstado);

		return fetchJoins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaByIdAndTipos(
	 * java.lang.Integer, br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo[])
	 */
	@Override
	public Empresa getEmpresaByIdAndTipos(Integer id, Tipo... tipos) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Empresa> cq = cb.createQuery(ENTITY_TYPE);

		Root<Empresa> from = cq.from(ENTITY_TYPE);

		from.fetch(Empresa_.cidade, JoinType.INNER).fetch(Cidade_.estado, JoinType.INNER);

		cq.select(from);

		cq.where(cb.equal(from.get(Empresa_.id), id),
				tipos.length == 0 ? cb.isNull(from.get(Empresa_.tipo))
						: cb.or(Arrays.asList(tipos).stream().map(tipo -> cb.equal(from.get(Empresa_.tipo), tipo))
								.toArray(Predicate[]::new)));

		TypedQuery<Empresa> tq = getEntityManager().createQuery(cq);

		try {
			Empresa empresa = tq.getSingleResult();

			return empresa;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("A empresa (id:%s, tipo: [%s]) não foi encontrada...", id,
					Arrays.asList(tipos).stream().map(tipo -> tipo.getDescricao()).collect(Collectors.joining(", "))));
		}
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
			throw new AppNotFoundException(String.format("O id da empresa (cnpj: %s) não foi encontrado...", cnpj));
		}
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
			throw new AppNotFoundException(String.format("O id da empresa (email: %s) não foi encontrado...", email));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#
	 * getUsuarioAcessoIdByEmpresaId(java.lang.Integer)
	 */
	@Override
	public Integer getUsuarioAcessoIdByEmpresaId(Integer empresaId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Empresa> from = cq.from(ENTITY_TYPE);
		Join<Empresa, Usuario> join = (Join<Empresa, Usuario>) from.join(Empresa_.usuarioAcesso, JoinType.INNER);

		cq.select(join.get(Usuario_.id));

		cq.where(cb.equal(from.get(Empresa_.id), empresaId));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer usuarioId = tq.getSingleResult();
			return usuarioId;
		} catch (NoResultException e) {
			throw new AppNotFoundException(
					String.format("Da empresa (id: %s), o id do usuário de acesso não foi encontrado...", empresaId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaIipoById(java.
	 * lang.Integer)
	 */
	@Override
	public Tipo getEmpresaIipoById(Integer id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tipo> cq = cb.createQuery(Tipo.class);

		Root<Empresa> from = cq.from(ENTITY_TYPE);
		cq.where(cb.equal(from.get(Empresa_.id), id));
		cq.select(from.get(Empresa_.tipo));

		TypedQuery<Tipo> tq = getEntityManager().createQuery(cq);

		try {
			Tipo tipo = tq.getSingleResult();
			return tipo;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("O tipo da empresa (id: %s) não foi encontrado...", id));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getUsuarioIdByEmpresaId(
	 * java.lang.Integer,
	 * br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao)
	 */
	@Override
	public Integer getUsuarioIdByEmpresaId(Integer empresaId, Relacao relacao) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Empresa> from = cq.from(ENTITY_TYPE);
		Join<Empresa, Usuario> join = from.join(relacao == ACESSO ? Empresa_.usuarioAcesso : Empresa_.usuarioCadastro,
				JoinType.INNER);

		cq.select(join.get(Usuario_.id));

		cq.where(cb.equal(from.get(Empresa_.id), empresaId));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer usuarioId = tq.getSingleResult();
			return usuarioId;
		} catch (NoResultException e) {
			throw new AppNotFoundException(
					String.format("Da empresa (id: %s), o id do usuário não foi encontrado...", empresaId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#
	 * getEmpresasByRazaoSocialOrCnpj(java.lang.String,
	 * br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo)
	 */
	@Override
	public Map<Integer, String> getEmpresasByRazaoSocialOrCnpj(String razaoSocialOrCnpj, Tipo tipo) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Empresa> from = cq.from(ENTITY_TYPE);

		cq.select(cb.array(from.get(Empresa_.id), from.get(Empresa_.razaoSocial), from.get(Empresa_.cnpj)));

		razaoSocialOrCnpj = "%" + razaoSocialOrCnpj.toLowerCase() + "%";

		cq.where(
				cb.or(cb.like(cb.lower(from.get(Empresa_.razaoSocial)), razaoSocialOrCnpj),
						cb.like(from.get(Empresa_.cnpj), razaoSocialOrCnpj)),
				cb.and(cb.equal(from.get(Empresa_.tipo), tipo), cb.notEqual(from.get(Empresa_.id), 1)));

		cq.orderBy(cb.asc(from.get(Empresa_.razaoSocial)));

		TypedQuery<Object[]> tq = getEntityManager().createQuery(cq);
		tq.setMaxResults(5);

		Map<Integer, String> empresas = tq.getResultList().stream()
				.collect(Collectors.toMap(empresa -> (Integer) empresa[0],
						empresa -> Empresa.getRazaoSocialWithCnpj((String) empresa[1], (String) empresa[2])));

		return empresas;
	}

}
