/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.Cliente;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.Pedido_;

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
	protected From<?, ?>[] getFetchJoins(Root<Pedido> from) {
		Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) from.fetch(Pedido_.cliente, JoinType.LEFT);
		Join<Pedido, Empresa> joinEmpresa = (Join<Pedido, Empresa>) from.fetch(Pedido_.empresa, JoinType.LEFT);
		return new From<?, ?>[] { joinCliente, joinEmpresa };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getJoins(javax.persistence.criteria.
	 * Root)
	 */
	@Override
	protected From<?, ?>[] getJoins(Root<Pedido> from) {
		Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) from.join(Pedido_.cliente, JoinType.LEFT);
		Join<Pedido, Empresa> joinEmpresa = (Join<Pedido, Empresa>) from.join(Pedido_.empresa, JoinType.LEFT);
		return new From<?, ?>[] { joinCliente, joinEmpresa };
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

		from.fetch(Pedido_.usuarioCadastro, JoinType.INNER);
		from.fetch(Pedido_.cliente, JoinType.LEFT);
		from.fetch(Pedido_.empresa, JoinType.LEFT);

		cq.select(from);

		cq.where(cb.equal(from.get(Pedido_.id), id));

		TypedQuery<Pedido> tq = getEntityManager().createQuery(cq);
		Pedido pedido = tq.getSingleResult();

		return pedido;
	}

}
