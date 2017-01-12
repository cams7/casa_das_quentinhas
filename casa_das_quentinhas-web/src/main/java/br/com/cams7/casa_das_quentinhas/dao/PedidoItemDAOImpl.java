/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
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
import br.com.cams7.casa_das_quentinhas.model.Pedido;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK_;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem_;
import br.com.cams7.casa_das_quentinhas.model.Pedido_;
import br.com.cams7.casa_das_quentinhas.model.Produto;

/**
 * @author César Magalhães
 *
 */
@Repository
public class PedidoItemDAOImpl extends AbstractDAO<PedidoItemPK, PedidoItem> implements PedidoItemDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<From<?, ?>> getFetchJoins(Root<PedidoItem> from) {
		List<From<?, ?>> fetchJoins = new ArrayList<>();

		boolean noneMatch = getIgnoredJoins() == null
				|| getIgnoredJoins().stream().noneMatch(type -> type.equals(Pedido.class));
		if (noneMatch) {
			Join<PedidoItem, Pedido> joinPedido = (Join<PedidoItem, Pedido>) from.fetch(PedidoItem_.pedido,
					JoinType.INNER);
			Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) joinPedido.fetch(Pedido_.cliente,
					JoinType.LEFT);
			Join<Pedido, Empresa> joinEmpresa = (Join<Pedido, Empresa>) joinPedido.fetch(Pedido_.empresa,
					JoinType.LEFT);

			fetchJoins.add(joinPedido);
			fetchJoins.add(joinCliente);
			fetchJoins.add(joinEmpresa);
		}

		noneMatch = getIgnoredJoins() == null
				|| getIgnoredJoins().stream().noneMatch(type -> type.equals(Produto.class));
		if (noneMatch) {
			Join<PedidoItem, Produto> joinProduto = (Join<PedidoItem, Produto>) from.fetch(PedidoItem_.produto,
					JoinType.INNER);

			fetchJoins.add(joinProduto);
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
	protected List<From<?, ?>> getJoins(Root<PedidoItem> from) {
		List<From<?, ?>> fetchJoins = new ArrayList<>();

		boolean noneMatch = getIgnoredJoins() == null
				|| getIgnoredJoins().stream().noneMatch(type -> type.equals(Pedido.class));
		if (noneMatch) {
			Join<PedidoItem, Pedido> joinPedido = (Join<PedidoItem, Pedido>) from.join(PedidoItem_.pedido,
					JoinType.INNER);
			Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) joinPedido.join(Pedido_.cliente, JoinType.LEFT);
			Join<Pedido, Empresa> joinEmpresa = (Join<Pedido, Empresa>) joinPedido.join(Pedido_.empresa, JoinType.LEFT);

			fetchJoins.add(joinPedido);
			fetchJoins.add(joinCliente);
			fetchJoins.add(joinEmpresa);
		}

		noneMatch = getIgnoredJoins() == null
				|| getIgnoredJoins().stream().noneMatch(type -> type.equals(Produto.class));
		if (noneMatch) {
			Join<PedidoItem, Produto> joinProduto = (Join<PedidoItem, Produto>) from.join(PedidoItem_.produto,
					JoinType.INNER);

			fetchJoins.add(joinProduto);
		}

		return fetchJoins;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoItemDAO#getItemById(java.lang.
	 * Long, java.lang.Integer)
	 */
	@Override
	public PedidoItem getItemById(Long pedidoId, Integer produtoId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<PedidoItem> cq = cb.createQuery(ENTITY_TYPE);

		Root<PedidoItem> from = cq.from(ENTITY_TYPE);

		from.fetch(PedidoItem_.produto, JoinType.INNER);

		cq.select(from);

		cq.where(cb.equal(from.get(PedidoItem_.id), new PedidoItemPK(pedidoId, produtoId)));

		TypedQuery<PedidoItem> tq = getEntityManager().createQuery(cq);

		try {
			PedidoItem item = tq.getSingleResult();
			return item;
		} catch (NoResultException e) {
			throw new AppNotFoundException(String
					.format("O item de pedido (pedido:%s, produto:%s) não foi encontrado...", pedidoId, produtoId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.PedidoItemDAO#deleteByPedido(java.
	 * lang.Long)
	 */
	@Override
	public int deleteByPedido(Long pedidoId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaDelete<PedidoItem> cd = cb.createCriteriaDelete(ENTITY_TYPE);

		Root<PedidoItem> from = cd.from(ENTITY_TYPE);

		cd.where(cb.equal(from.get(PedidoItem_.id).get(PedidoItemPK_.pedidoId), pedidoId));

		int deleted = getEntityManager().createQuery(cd).executeUpdate();

		return deleted;
	}

}
