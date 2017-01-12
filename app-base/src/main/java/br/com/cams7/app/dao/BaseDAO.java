package br.com.cams7.app.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.cams7.app.SearchParams;
import br.com.cams7.app.entity.AbstractEntity;

/**
 * @author César Magalhães
 *
 * @param <PK>
 *            ID da entidade
 * @param <E>
 *            Entidade
 */
public interface BaseDAO<PK extends Serializable, E extends AbstractEntity<PK>> {

	/**
	 * Retorna um objeto que é instância de "AbstractEntity", filtrando-o pelo
	 * id
	 * 
	 * @param id
	 *            Id da entidade
	 * @return Entidade
	 */
	E getById(PK id);

	/**
	 * Salva um objeto que é instância de "AbstractEntity"
	 * 
	 * @param entity
	 *            Entidade
	 */
	void persist(E entity);

	/**
	 * Atualiza um objeto que é instância de "AbstractEntity"
	 * 
	 * @param entity
	 *            Entidade
	 */
	void update(E entity);

	/**
	 * Remove um objeto que é instância de "AbstractEntity", filtrando-o pelo id
	 * 
	 * @param id
	 *            Id Entidade
	 */
	void delete(PK id);

	/**
	 * Retorna todos os objetos que são instâncias de "AbstractEntity"
	 * 
	 * @return Entidades
	 */
	List<E> getAll();

	/**
	 * Filtra, pagina e ordena os objetos que são instâncias de "AbstractEntity"
	 * 
	 * @params parâmetros usados na busca
	 * 
	 * @return Entidades
	 */
	List<E> search(SearchParams params);

	/**
	 * Retorna o número total de instâncias de "AbstractEntity". Essa pesquisa é
	 * feita com auxílio de filtros
	 * 
	 * @param filters
	 *            Filtros
	 * @param globalFilters
	 *            Nomes dos atributos da entidade
	 * @return
	 */
	long getTotalElements(Map<String, Object> filters, String... globalFilters);

	/**
	 * Retorna o número total de instâncias de "AbstractEntity"
	 * 
	 * @return
	 */
	long count();

	/**
	 * @param ignoredJoins
	 *            Joins que serão ignorados
	 */
	void setIgnoredJoins(@SuppressWarnings("unchecked") Class<? extends AbstractEntity<?>>... ignoredJoins);

}
