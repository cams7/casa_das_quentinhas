/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.cams7.app.dao.AbstractDAO;
import br.com.cams7.casa_das_quentinhas.model.PedidoItem;
import br.com.cams7.casa_das_quentinhas.model.PedidoItemPK;

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

}
