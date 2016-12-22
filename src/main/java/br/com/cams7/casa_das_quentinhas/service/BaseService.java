/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.io.Serializable;

import br.com.cams7.casa_das_quentinhas.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
public interface BaseService<E extends AbstractEntity<PK>, PK extends Serializable> extends BaseDAO<E, PK> {

}
