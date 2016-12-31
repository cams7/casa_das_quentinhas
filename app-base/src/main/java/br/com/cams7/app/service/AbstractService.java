/**
 * 
 */
package br.com.cams7.app.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.app.model.AbstractEntity;
import br.com.cams7.app.utils.SearchParams;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#getById(java.io.Serializable)
	 */
	@Override
	public E getById(PK id) {
		E entity = getDao().getById(id);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#persist(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void persist(E entity) {
		getDao().persist(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#update(br.com.cams7.app.model.
	 * AbstractEntity)
	 */
	@Override
	public void update(E entity) {
		getDao().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#delete(java.io.Serializable)
	 */
	@Override
	public void delete(PK id) {
		getDao().delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#getAll()
	 */
	@Override
	public List<E> getAll() {
		List<E> entities = getDao().getAll();
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.dao.BaseDAO#search(br.com.cams7.app.utils.SearchParams)
	 */
	@Override
	public List<E> search(SearchParams params) {
		List<E> entities = getDao().search(params);
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#getTotalElements(java.util.Map,
	 * java.lang.String[])
	 */
	@Override
	public long getTotalElements(Map<String, Object> filters, String... globalFilters) {
		long total = getDao().getTotalElements(filters, globalFilters);
		return total;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.dao.BaseDAO#count()
	 */
	@Override
	public long count() {
		long count = getDao().count();
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.service.BaseService#persist(br.com.cams7.app.model.
	 * AbstractEntity, java.lang.String)
	 */
	@Override
	public void persist(E entity, String userName) {
		persist(entity);
	}

}
