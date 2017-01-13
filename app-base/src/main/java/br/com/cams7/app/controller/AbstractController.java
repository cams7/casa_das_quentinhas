/**
 * 
 */
package br.com.cams7.app.controller;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cams7.app.AbstractBase;
import br.com.cams7.app.entity.AbstractEntity;
import br.com.cams7.app.service.BaseService;

/**
 * @author César Magalhães
 *
 * @param <PK>
 *            ID da entidade
 * @param <E>
 *            Entidade
 * @param <S>
 *            Service
 */
public abstract class AbstractController<PK extends Serializable, E extends AbstractEntity<PK>, S extends BaseService<PK, E>>
		extends AbstractBase<PK, E> {

	@Autowired
	private S service;

	public AbstractController() {
		super();
	}

	protected final S getService() {
		return service;
	}

	@SuppressWarnings("unchecked")
	protected final void setIgnoredJoins() {
		if (getIgnoredJoins() == null)
			getService().setIgnoredJoins();
		else
			getService().setIgnoredJoins(getIgnoredJoins());
	}

	/**
	 * @return Filtros para consulta
	 */
	protected Map<String, Object> getFilters() {
		return null;
	}

	/**
	 * @return Joins que serão ignorados
	 */
	@SuppressWarnings("hiding")
	protected <E extends AbstractEntity<?>> Class<E>[] getIgnoredJoins() {
		return null;
	}

}
