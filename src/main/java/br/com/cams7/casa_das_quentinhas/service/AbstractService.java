/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.casa_das_quentinhas.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.AbstractEntity;
import br.com.cams7.casa_das_quentinhas.utils.SearchParams;

/**
 * @author César Magalhães
 *
 */
@Transactional
public abstract class AbstractService<DAO extends BaseDAO<E, PK>, E extends AbstractEntity<PK>, PK extends Serializable>
		implements BaseService<E, PK> {

	@Autowired
	private DAO dao;

	protected DAO getDao() {
		return dao;
	}

	@Override
	public E getById(PK id) {
		E entity = getDao().getById(id);
		return entity;
	}

	@Override
	public void persist(E entity) {
		getDao().persist(entity);
	}

	@Override
	public void update(E entity) {
		getDao().update(entity);
	}

	@Override
	public void delete(PK id) {
		getDao().delete(id);
	}

	@Override
	public List<E> getAll() {
		List<E> entities = getDao().getAll();
		return entities;
	}

	@Override
	public List<E> search(SearchParams params) {
		List<E> entities = getDao().search(params);
		return entities;
	}

	@Override
	public long getTotalElements(Map<String, Object> filters, String... globalFilters) {
		long total = getDao().getTotalElements(filters, globalFilters);
		return total;
	}

	@Override
	public long count() {
		long count = getDao().count();
		return count;
	}

}
