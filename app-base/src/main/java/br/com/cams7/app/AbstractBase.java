/**
 * 
 */
package br.com.cams7.app;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cams7.app.entity.AbstractEntity;

/**
 * @author César Magalhães
 * 
 * @param <PK>
 *            ID da entidade
 * @param <E>
 *            Entidade
 */
public abstract class AbstractBase<PK extends Serializable, E extends AbstractEntity<PK>> {

	protected final Class<E> ENTITY_TYPE;
	protected final Logger LOGGER;

	public static final Locale LOCALE = new Locale("pt", "BR");

	/**
	 * 
	 */
	public AbstractBase() {
		super();

		ENTITY_TYPE = getEntityType();
		LOGGER = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Class<E> getEntityType() {
		try {
			return (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		} catch (ClassCastException e) {
		}

		return (Class<E>) ((ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

}
