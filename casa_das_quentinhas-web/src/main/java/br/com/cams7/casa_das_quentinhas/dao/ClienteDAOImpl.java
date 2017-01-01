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
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Cliente_;
import br.com.cams7.casa_das_quentinhas.model.Contato_;
import br.com.cams7.casa_das_quentinhas.model.Estado;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.model.Usuario_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class ClienteDAOImpl extends AbstractDAO<Cliente, Integer> implements ClienteDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected From<?, ?>[] getFetchJoins(Root<Cliente> from) {
		Join<Cliente, Cidade> joinCidade = (Join<Cliente, Cidade>) from.fetch(Cliente_.cidade, JoinType.LEFT);
		Join<Cidade, Estado> joinEstado = (Join<Cidade, Estado>) joinCidade.fetch(Cidade_.estado, JoinType.LEFT);

		return new From<?, ?>[] { joinCidade, joinEstado };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getJoins(javax.persistence.criteria.
	 * Root)
	 */
	@Override
	protected From<?, ?>[] getJoins(Root<Cliente> from) {
		Join<Cliente, Cidade> joinCidade = (Join<Cliente, Cidade>) from.join(Cliente_.cidade, JoinType.LEFT);
		Join<Cidade, Estado> joinEstado = (Join<Cidade, Estado>) joinCidade.join(Cidade_.estado, JoinType.LEFT);

		return new From<?, ?>[] { joinCidade, joinEstado };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteById(java.lang.
	 * Integer)
	 */
	@Override
	public Cliente getClienteById(Integer id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Cliente> cq = cb.createQuery(ENTITY_TYPE);

		Root<Cliente> from = cq.from(ENTITY_TYPE);

		from.fetch(Cliente_.usuarioAcesso, JoinType.LEFT);
		from.fetch(Cliente_.usuarioCadastro, JoinType.LEFT);
		from.fetch(Cliente_.cidade, JoinType.LEFT).fetch(Cidade_.estado, JoinType.LEFT);

		cq.select(from);

		cq.where(cb.equal(from.get(Cliente_.id), id));

		TypedQuery<Cliente> tq = getEntityManager().createQuery(cq);
		Cliente cliente = tq.getSingleResult();

		return cliente;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteIdByCpf(java.
	 * lang.String)
	 */
	@Override
	public Integer getClienteIdByCpf(String cpf) {
		LOGGER.info("CPF : {}", cpf);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Cliente> from = cq.from(ENTITY_TYPE);

		cq.select(from.get(Cliente_.id));

		cq.where(cb.equal(from.get(Cliente_.cpf), cpf));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer clienteId = tq.getSingleResult();
			return clienteId;
		} catch (NoResultException e) {
			LOGGER.warn("CPF not found...");
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#getClienteIdByEmail(java.
	 * lang.String)
	 */
	@Override
	public Integer getClienteIdByEmail(String email) {
		LOGGER.info("E-mail : {}", email);

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Cliente> from = cq.from(ENTITY_TYPE);

		cq.select(from.get(Cliente_.id));

		cq.where(cb.equal(from.get(Cliente_.contato).get(Contato_.email), email));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer clienteId = tq.getSingleResult();
			return clienteId;
		} catch (NoResultException e) {
			LOGGER.warn("E-mail not found...");
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#
	 * getUsuarioAcessoIdByClienteId(java.lang.Integer)
	 */
	@Override
	public Integer getUsuarioAcessoIdByClienteId(Integer clienteId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Cliente> from = cq.from(ENTITY_TYPE);
		Join<Cliente, Usuario> join = (Join<Cliente, Usuario>) from.join(Cliente_.usuarioAcesso, JoinType.LEFT);

		cq.select(join.get(Usuario_.id));

		cq.where(cb.equal(from.get(Cliente_.id), clienteId));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer usuarioId = tq.getSingleResult();
			return usuarioId;
		} catch (NoResultException e) {
			LOGGER.warn("E-mail not found...");
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.ClienteDAO#
	 * getClientesByNomeOrCpfOrTelefone(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getClientesByNomeOrCpfOrTelefone(String nomeOrCpfOrTelefone) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Cliente> from = cq.from(ENTITY_TYPE);

		cq.select(cb.array(from.get(Cliente_.id), from.get(Cliente_.nome), from.get(Cliente_.cpf)));

		nomeOrCpfOrTelefone = "%" + nomeOrCpfOrTelefone.toLowerCase() + "%";

		cq.where(cb.or(cb.like(cb.lower(from.get(Cliente_.nome)), nomeOrCpfOrTelefone),
				cb.like(from.get(Cliente_.cpf), nomeOrCpfOrTelefone),
				cb.like(cb.lower(from.get(Cliente_.contato).get(Contato_.telefone)), nomeOrCpfOrTelefone)));

		cq.orderBy(cb.asc(from.get(Cliente_.nome)));

		TypedQuery<Object[]> tq = getEntityManager().createQuery(cq);
		tq.setMaxResults(5);

		Map<Integer, String> clientes = tq.getResultList().stream()
				.collect(Collectors.toMap(cliente -> (Integer) cliente[0],
						cliente -> Cliente.getNomeWithCpf((String) cliente[1], (String) cliente[2])));

		return clientes;
	}

}
