package br.com.cams7.app.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cams7.app.model.AbstractEntity;
import br.com.cams7.app.utils.AppHelper;
import br.com.cams7.app.utils.SearchParams;

public abstract class AbstractDAO<E extends AbstractEntity<PK>, PK extends Serializable> implements BaseDAO<E, PK> {

	protected final Class<E> ENTITY_TYPE;
	protected final Logger LOGGER;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		super();

		ENTITY_TYPE = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		LOGGER = LoggerFactory.getLogger(this.getClass());
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
		cq.orderBy(cb.desc(from.<PK>get("id")));

		TypedQuery<E> tq = getEntityManager().createQuery(cq);
		List<E> entities = tq.getResultList();
		return entities;
	}

	/**
	 * @param fromWithJoins
	 * @param attributeName
	 * @return
	 */
	private FromOrJoin getFromOrJoin(From<?, ?>[] fromWithJoins, String attributeName) {
		From<?, ?> fromOrJoin = fromWithJoins[0];

		String[] atributes = attributeName.split("\\.");

		if (atributes.length > 1) {
			Class<E> entityType = AppHelper.getFieldTypes(ENTITY_TYPE, attributeName).getEntityType();

			boolean isEntity = entityType.isAnnotationPresent(Entity.class);

			if (isEntity) {
				attributeName = atributes[atributes.length - 1];
				String entityName = atributes[atributes.length - 2];

				for (short i = 1; i < fromWithJoins.length; i++) {
					Join<?, ?> join = (Join<?, ?>) fromWithJoins[i];

					if (entityType.equals(join.getJavaType()) && entityName.equals(join.getAttribute().getName())) {
						fromOrJoin = join;
						break;
					}
				}
			}
		}

		return new FromOrJoin(attributeName, fromOrJoin);
	}

	private Expression<?> getExpression(FromOrJoin fromOrJoin) {
		String[] atributes = fromOrJoin.attributeName.split("\\.");

		Path<?> expression = fromOrJoin.from.get(atributes[0]);

		for (short i = 1; i < atributes.length; i++)
			expression = expression.get(atributes[i]);

		return expression;
	}

	/**
	 * @param cb
	 * @param fromWithJoins
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Predicate getExpression(CriteriaBuilder cb, From<?, ?>[] fromWithJoins, String fieldName,
			Object fieldValue) {
		Object value = AppHelper.getFieldValue(ENTITY_TYPE, fieldName, fieldValue);

		if (value == null)
			return null;

		Class<?> fieldType = value.getClass();

		FromOrJoin fromOrJoin = getFromOrJoin(fromWithJoins, fieldName);

		Predicate predicate;

		if (AppHelper.isBoolean(fieldType))
			if (((Boolean) value).booleanValue())
				predicate = cb.isTrue((Expression<Boolean>) getExpression(fromOrJoin));
			else
				predicate = cb.isFalse((Expression<Boolean>) getExpression(fromOrJoin));
		else if (AppHelper.isNumber(fieldType))
			predicate = cb.like(cb.lower((Expression<String>) getExpression(fromOrJoin)),
					"%" + String.valueOf(value).toLowerCase() + "%");
		else if (AppHelper.isDate(fieldType))
			predicate = cb.equal(getExpression(fromOrJoin), value);
		else if (AppHelper.isEnum(fieldType))
			predicate = cb.equal(getExpression(fromOrJoin), value);
		else
			predicate = cb.like(cb.lower((Expression<String>) getExpression(fromOrJoin)),
					"%" + String.valueOf(value).toLowerCase() + "%");

		return predicate;
	}

	/**
	 * Filtra
	 * 
	 * @param cb
	 * @param cq
	 * @param fromWithJoins
	 * @param filters
	 * @param globalFilters
	 * @return
	 */
	protected CriteriaQuery<?> getFilter(CriteriaBuilder cb, CriteriaQuery<?> cq, From<?, ?>[] fromWithJoins,
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

				Predicate expression = getExpression(cb, fromWithJoins, filter.getKey(), filter.getValue());
				if (expression != null)
					and.add(expression);
			}

			if (!and.isEmpty())
				predicates.add(cb.and(and.toArray(new Predicate[] {})));

			if (containsKeyGlobalFilter && globalFilters.length > 0) {
				Set<Predicate> or = new HashSet<>();
				for (String globalFilter : globalFilters) {
					Predicate expression = getExpression(cb, fromWithJoins, globalFilter,
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
	 * @param fromWithJoins
	 *            From<?, ?>
	 * @param params
	 *            parâmetros
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected TypedQuery<E> getFilterAndPaginationAndSorting(CriteriaBuilder cb, CriteriaQuery<E> cq,
			From<?, ?>[] fromWithJoins, SearchParams params) {
		cq = (CriteriaQuery<E>) getFilter(cb, cq, fromWithJoins, params.getFilters(), params.getGlobalFilters());

		if (params.getSortField() != null && params.getSortOrder() != null) {
			FromOrJoin fromOrJoin = getFromOrJoin(fromWithJoins, params.getSortField());

			Order order;

			switch (params.getSortOrder()) {
			case ASCENDING:
				order = cb.asc(getExpression(fromOrJoin));
				break;
			case DESCENDING:
				order = cb.desc(getExpression(fromOrJoin));
				break;

			default:
				order = cb.desc(fromOrJoin.from.get("id"));
				break;
			}
			cq = cq.orderBy(order);
		}

		Root<E> root = (Root<E>) fromWithJoins[0];
		cq.select(root);

		TypedQuery<E> tq = getEntityManager().createQuery(cq);
		if (params.getFirstPage() != null)
			tq.setFirstResult(params.getFirstPage());

		if (params.getSizePage() != null)
			tq.setMaxResults(params.getSizePage());

		return tq;
	}

	protected From<?, ?>[] getFromWithFetchJoins(Root<E> from) {
		From<?, ?>[] fromWithJoins = new From<?, ?>[] { from };
		return fromWithJoins;
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
		From<?, ?>[] fromWithJoins = getFromWithFetchJoins(from);

		TypedQuery<E> tq = getFilterAndPaginationAndSorting(cb, cq, fromWithJoins, params);
		List<E> entities = tq.getResultList();
		return entities;
	}

	/**
	 * Retorna o número total de instâncias. Essa pesquisa é feita com auxilio
	 * de filtros
	 * 
	 * @param cb
	 * @param cq
	 * @param fromWithJoins
	 * @return
	 */
	protected long getCount(CriteriaBuilder cb, CriteriaQuery<Long> cq, From<?, ?>[] fromWithJoins) {
		@SuppressWarnings("unchecked")
		Root<E> root = (Root<E>) fromWithJoins[0];
		Expression<Long> count = cb.count(root);
		cq.select(count);

		Long countValue = getEntityManager().createQuery(cq).getSingleResult();
		return countValue;
	}

	protected From<?, ?>[] getFromWithJoins(Root<E> from) {
		From<?, ?>[] fromWithJoins = new From<?, ?>[] { from };
		return fromWithJoins;
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
		From<?, ?>[] fromWithJoins = getFromWithJoins(from);

		cq = (CriteriaQuery<Long>) getFilter(cb, cq, fromWithJoins, filters, globalFilters);
		long count = getCount(cb, cq, fromWithJoins);

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
		From<?, ?>[] fromWithJoins = getFromWithJoins(from);

		long count = getCount(cb, cq, fromWithJoins);

		return count;
	}

	private class FromOrJoin {
		private String attributeName;
		private From<?, ?> from;

		private FromOrJoin(String attributeName, From<?, ?> from) {
			super();

			this.attributeName = attributeName;
			this.from = from;
		}
	}

}
