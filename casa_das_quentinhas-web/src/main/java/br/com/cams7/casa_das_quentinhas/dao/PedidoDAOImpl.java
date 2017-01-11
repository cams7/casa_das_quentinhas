/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.ArrayList;
import java.util.List;

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
import br.com.cams7.app.utils.AppNotFoundException;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Manutencao_;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.Pedido_;
import br.com.cams7.casa_das_quentinhas.model.Usuario_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class PedidoDAOImpl extends AbstractDAO<Pedido, Long> implements PedidoDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<From<?, ?>> getFetchJoins(Root<Pedido> from) {
		List<From<?, ?>> fetchJoins = new ArrayList<>();

		boolean noneMatch = getIgnoredJoins() == null
				|| getIgnoredJoins().stream().noneMatch(type -> type.equals(Cliente.class));
		if (noneMatch) {
			Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) from.fetch(Pedido_.cliente, JoinType.LEFT);
			fetchJoins.add(joinCliente);
		}

		noneMatch = getIgnoredJoins() == null
				|| getIgnoredJoins().stream().noneMatch(type -> type.equals(Empresa.class));
		if (noneMatch) {
			Join<Pedido, Empresa> joinEmpresa = (Join<Pedido, Empresa>) from.fetch(Pedido_.empresa, JoinType.LEFT);

			fetchJoins.add(joinEmpresa);
		}

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
	protected List<From<?, ?>> getJoins(Root<Pedido> from) {
		List<From<?, ?>> fetchJoins = new ArrayList<>();

		boolean noneMatch = getIgnoredJoins() == null
				|| getIgnoredJoins().stream().noneMatch(type -> type.equals(Cliente.class));
		if (noneMatch) {
			Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) from.join(Pedido_.cliente, JoinType.LEFT);
			fetchJoins.add(joinCliente);
		}

		noneMatch = getIgnoredJoins() == null
				|| getIgnoredJoins().stream().noneMatch(type -> type.equals(Empresa.class));
		if (noneMatch) {
			Join<Pedido, Empresa> joinEmpresa = (Join<Pedido, Empresa>) from.join(Pedido_.empresa, JoinType.LEFT);
			fetchJoins.add(joinEmpresa);
		}

		return fetchJoins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoDAO#getPedidoById(java.lang.
	 * Long)
	 */
	@Override
	public Pedido getPedidoById(Long id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Pedido> cq = cb.createQuery(ENTITY_TYPE);

		Root<Pedido> from = cq.from(ENTITY_TYPE);

		from.fetch(Pedido_.cliente, JoinType.LEFT);
		from.fetch(Pedido_.empresa, JoinType.LEFT);

		cq.select(from);

		cq.where(cb.equal(from.get(Pedido_.id), id));

		TypedQuery<Pedido> tq = getEntityManager().createQuery(cq);

		try {
			Pedido pedido = tq.getSingleResult();
			return pedido;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format("O pedido (id:%s) não foi encontrado...", id));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoDAO#getUsuarioIdByPedidoId(
	 * java.lang.Long)
	 */
	@Override
	public Integer getUsuarioIdByPedidoId(Long pedidoId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

		Root<Pedido> from = cq.from(ENTITY_TYPE);

		cq.select(from.join(Pedido_.usuarioCadastro, JoinType.INNER).get(Usuario_.id));

		cq.where(cb.equal(from.get(Pedido_.id), pedidoId));

		TypedQuery<Integer> tq = getEntityManager().createQuery(cq);

		try {
			Integer usuarioId = tq.getSingleResult();
			return usuarioId;
		} catch (NoResultException e) {
			throw new AppNotFoundException(
					String.format("Do pedido (id: %s), o id do usuário não foi encontrado...", pedidoId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.PedidoDAO#
	 * getUsuarioIdAndPedidoCadastroByPedidoId(java.lang.Long)
	 */
	@Override
	public Object[] getUsuarioIdAndPedidoCadastroByPedidoId(Long pedidoId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

		Root<Pedido> from = cq.from(ENTITY_TYPE);
		cq.select(cb.array(from.join(Pedido_.usuarioCadastro, JoinType.INNER).get(Usuario_.id),
				from.get(Pedido_.manutencao).get(Manutencao_.cadastro)));
		cq.where(cb.equal(from.get(Pedido_.id), pedidoId));

		TypedQuery<Object[]> tq = getEntityManager().createQuery(cq);

		try {
			Object[] pedido = tq.getSingleResult();
			return pedido;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String.format(
					"Do pedido (id: %s), o id do usuário e a data de cadastro do pedido não foram encontrados...",
					pedidoId));
		}
	}

}
