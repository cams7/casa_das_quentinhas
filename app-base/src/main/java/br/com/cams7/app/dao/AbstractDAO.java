package br.com.cams7.app.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.cams7.app.model.AbstractEntity;
import br.com.cams7.app.utils.AppHelper;
import br.com.cams7.app.utils.SearchParams;

public abstract class AbstractDAO<E extends AbstractEntity<PK>, PK extends Serializable> implements BaseDAO<E, PK> {

	protected final Class<E> ENTITY_TYPE;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		super();

		ENTITY_TYPE = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@PersistenceContext
	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.BaseDAO#getByKey(java.io.
	 * Serializable)
	 */
	@Override
	public E getById(PK id) {
		E entity = getEntityManager().find(ENTITY_TYPE, id);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.BaseDAO#persist(br.com.cams7.
	 * casa_das_quentinhas.model.AbstractEntity)
	 */
	@Override
	public void persist(E entity) {
		getEntityManager().persist(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.BaseDAO#update(br.com.cams7.
	 * casa_das_quentinhas.model.AbstractEntity)
	 */
	@Override
	public void update(E entity) {
		getEntityManager().merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.BaseDAO#delete(br.com.cams7.
	 * casa_das_quentinhas.model.AbstractEntity)
	 */
	@Override
	public void delete(PK id) {
		E entity = getById(id);
		getEntityManager().remove(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.BaseDAO#getAll()
	 */
	@Override
	public List<E> getAll() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(ENTITY_TYPE);

		Root<E> from = cq.from(ENTITY_TYPE);
		cq.select(from);
		cq.orderBy(cb.desc(from.get("id")));

		TypedQuery<E> tq = getEntityManager().createQuery(cq);
		List<E> entities = tq.getResultList();
		return entities;
	}

	@SuppressWarnings("unchecked")
	private Root<E> getRoot(From<?, ?> from) {
		if (from instanceof Root<?>)
			return (Root<E>) from;

		from = ((Join<?, ?>) from).getParent();
		return getRoot(from);
	}

	private From<?, ?> getFrom(From<?, ?> from, Class<E> entityType) {
		if (from.getJavaType().equals(entityType) || from instanceof Root<?>)
			return from;

		from = ((Join<?, ?>) from).getParent();
		return getFrom(from, entityType);
	}

	private AttributeFrom getFrom(From<?, ?> from, String attributeName) {
		Class<E> entityType = AppHelper.getFieldTypes(ENTITY_TYPE, attributeName).getEntityType();

		attributeName = attributeName.substring(attributeName.lastIndexOf(".") + 1);
		from = getFrom(from, entityType);

		return new AttributeFrom(attributeName, from);
	}

	private Predicate getExpression(CriteriaBuilder cb, From<?, ?> from, String fieldName, Object fieldValue) {
		Object value = AppHelper.getFieldValue(ENTITY_TYPE, fieldName, fieldValue);

		if (value == null)
			return null;

		Class<?> fieldType = value.getClass();

		AttributeFrom attributeFrom = getFrom(from, fieldName);
		fieldName = attributeFrom.attributeName;
		from = attributeFrom.from;

		Predicate predicate;

		if (AppHelper.isBoolean(fieldType))
			if (((Boolean) value).booleanValue())
				predicate = cb.isTrue(from.<Boolean>get(fieldName));
			else
				predicate = cb.isFalse(from.<Boolean>get(fieldName));
		else if (AppHelper.isNumber(fieldType))
			predicate = cb.like(cb.lower(from.<String>get(fieldName)), "%" + String.valueOf(value).toLowerCase() + "%");
		else if (AppHelper.isDate(fieldType))
			predicate = cb.equal(from.get(fieldName), value);
		else if (AppHelper.isEnum(fieldType))
			predicate = cb.equal(from.get(fieldName), value);
		else
			predicate = cb.like(cb.lower(from.<String>get(fieldName)), "%" + String.valueOf(value).toLowerCase() + "%");

		return predicate;
	}

	/**
	 * Filtra
	 * 
	 * @param cb
	 * @param cq
	 * @param from
	 * @param filters
	 * @param globalFilters
	 * @return
	 */
	private CriteriaQuery<?> getFilter(CriteriaBuilder cb, CriteriaQuery<?> cq, From<?, ?> from,
			Map<String, Object> filters, String... globalFilters) {
		if (filters != null && !filters.isEmpty()) {
			boolean containsKeyGlobalFilter = false;

			Set<Predicate> predicates = new HashSet<>();
			Set<Predicate> and = new HashSet<>();
			for (Entry<String, Object> filter : filters.entrySet()) {
				if (SearchParams.GLOBAL_FILTER.equals(filter.getKey())) {
					containsKeyGlobalFilter = true;
					continue;
				}

				Predicate expression = getExpression(cb, from, filter.getKey(), filter.getValue());
				if (expression != null)
					and.add(expression);
			}

			if (!and.isEmpty())
				predicates.add(cb.and(and.toArray(new Predicate[] {})));

			if (containsKeyGlobalFilter && globalFilters.length > 0) {
				Set<Predicate> or = new HashSet<>();
				for (String globalFilter : globalFilters) {
					Predicate expression = getExpression(cb, from, globalFilter,
							filters.get(SearchParams.GLOBAL_FILTER));
					if (expression != null)
						or.add(expression);
				}
				if (!or.isEmpty())
					predicates.add(cb.or(or.toArray(new Predicate[] {})));
			}
			if (!predicates.isEmpty())
				cq = cq.where(predicates.toArray(new Predicate[] {}));
		}
		return cq;
	}

	/**
	 * Filtra, pagina e ordena
	 * 
	 * @param cb
	 *            CriteriaBuilder
	 * @param cq
	 *            CriteriaQuery<E>
	 * @param from
	 *            From<?, ?>
	 * @param params
	 *            parâmetros
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TypedQuery<E> getFilterAndPaginationAndSorting(CriteriaBuilder cb, CriteriaQuery<E> cq, From<?, ?> from,
			SearchParams params) {
		cq = (CriteriaQuery<E>) getFilter(cb, cq, from, params.getFilters(), params.getGlobalFilters());

		if (params.getSortField() != null && params.getSortOrder() != null) {
			AttributeFrom attributeFrom = getFrom(from, params.getSortField());
			params.setSortField(attributeFrom.attributeName);
			From<?, ?> sortFrom = attributeFrom.from;

			Order order;

			switch (params.getSortOrder()) {
			case ASCENDING:
				order = cb.asc(sortFrom.get(params.getSortField()));
				break;
			case DESCENDING:
				order = cb.desc(sortFrom.get(params.getSortField()));
				break;

			default:
				order = cb.asc(sortFrom.get("id"));
				break;
			}
			cq = cq.orderBy(order);
		}

		Root<E> root = getRoot(from);
		cq.select(root);

		TypedQuery<E> tq = getEntityManager().createQuery(cq);
		if (params.getFirstPage() != null)
			tq.setFirstResult(params.getFirstPage());

		if (params.getSizePage() != null)
			tq.setMaxResults(params.getSizePage());

		return tq;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.BaseDAO#search(br.com.cams7.
	 * casa_das_quentinhas.utils.SearchParams)
	 */
	@Override
	public List<E> search(SearchParams params) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(ENTITY_TYPE);

		Root<E> from = cq.from(ENTITY_TYPE);

		TypedQuery<E> tq = getFilterAndPaginationAndSorting(cb, cq, from, params);
		List<E> entities = tq.getResultList();
		return entities;
	}

	/**
	 * Retorna o numero total de instâncias de "AbstractEntity". Essa pesquisa é
	 * feita com auxilio de filtros
	 * 
	 * @param select
	 * @return
	 */
	private long getCount(CriteriaBuilder cb, CriteriaQuery<Long> cq, From<?, ?> from) {
		Root<E> root = getRoot(from);
		Expression<Long> count = cb.count(root);
		cq.select(count);

		Long countValue = getEntityManager().createQuery(cq).getSingleResult();
		return countValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.BaseDAO#getTotalElements(java.util.
	 * Map, java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public long getTotalElements(Map<String, Object> filters, String... globalFilters) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<E> from = cq.from(ENTITY_TYPE);
		cq = (CriteriaQuery<Long>) getFilter(cb, cq, from, filters, globalFilters);
		long count = getCount(cb, cq, from);

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.BaseDAO#count()
	 */
	@Override
	public long count() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<E> from = cq.from(ENTITY_TYPE);
		long count = getCount(cb, cq, from);

		return count;
	}

	private class AttributeFrom {
		private String attributeName;
		private From<?, ?> from;

		private AttributeFrom(String attributeName, From<?, ?> from) {
			super();
			this.attributeName = attributeName;
			this.from = from;
		}
	}

}
