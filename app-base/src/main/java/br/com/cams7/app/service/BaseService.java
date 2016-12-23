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
 */
public interface BaseService<E extends AbstractEntity<PK>, PK extends Serializable> extends BaseDAO<E, PK> {

}
