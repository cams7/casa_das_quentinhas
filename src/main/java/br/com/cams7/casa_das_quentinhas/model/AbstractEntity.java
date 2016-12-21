/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import java.io.Serializable;

/**
 * Classe comum a todas entidades
 * 
 * @author cesar
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractEntity<PK extends Serializable> implements Serializable {

	public AbstractEntity() {
		super();
	}

	/**
	 * @param id
	 */
	public AbstractEntity(PK id) {
		this();

		setId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s{id:%s}", this.getClass().getSimpleName(), getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {

		if (null == object)
			return false;

		if (this == object)
			return true;

		if (!getClass().equals(object.getClass()))
			return false;

		@SuppressWarnings("unchecked")
		AbstractEntity<PK> entity = (AbstractEntity<PK>) object;
		if (getId() == null) {
			if (entity.getId() != null)
				return false;
		} else if (!getId().equals(entity.getId()))
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hashCode = 1;
		hashCode = PRIME * hashCode + ((getId() == null) ? 0 : getId().hashCode());
		return hashCode;
	}

	/**
	 * Id da entidade
	 * 
	 * @return Long
	 */
	public abstract PK getId();

	/**
	 * @param id
	 *            da entitade
	 */
	public abstract void setId(PK id);

}
