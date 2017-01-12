/**
 * 
 */
package br.com.cams7.app.service;

import java.io.Serializable;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.app.model.AbstractEntity;

/**
 * @author César Magalhães
 *
 * @param <PK>
 *            ID da entidade
 * @param <E>
 *            Entidade
 */
public interface BaseService<PK extends Serializable, E extends AbstractEntity<PK>> extends BaseDAO<PK, E> {

	/**
	 * @param username
	 *            E-mail do usuário logado
	 */
	void setUsername(String username);
}
