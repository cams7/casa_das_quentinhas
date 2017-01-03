/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK_;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem_;

/**
 * @author César Magalhães
 *
 */
@Repository
public class PedidoItemDAOImpl extends AbstractDAO<PedidoItem, PedidoItemPK> implements PedidoItemDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.AbstractDAO#getFetchJoins(javax.persistence.criteria
	 * .Root)
	 */
	@Override
	protected From<?, ?>[] getFetchJoins(Root<PedidoItem> from) {
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
	protected From<?, ?>[] getJoins(Root<PedidoItem> from) {
		return null;
	}

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
