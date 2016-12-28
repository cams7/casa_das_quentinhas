package br.com.cams7.app.dao;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import br.com.cams7.app.model.AbstractEntity;
import br.com.cams7.app.utils.SearchParams;

public interface BaseDAO<E extends AbstractEntity<PK>, PK extends Serializable> {

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
	Set<E> getAll();

	/**
	 * Filtra, pagina e ordena os objetos que são instâncias de "AbstractEntity"
	 * 
	 * @params parâmetros usados na busca
	 * 
	 * @return Entidades
	 */
	Set<E> search(SearchParams params);

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

}
