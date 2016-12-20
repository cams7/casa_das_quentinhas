/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import java.io.Serializable;

/**
 * Classe comum a todas entidades
 * 
 * @author cesar
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {

	public AbstractEntity() {
		super();
	}

	/**
	 * @param id
	 */
	public AbstractEntity(ID id) {
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
	public boolean equals(Object entity) {

		if (null == entity)
			return false;

		if (this == entity)
			return true;

		if (!getClass().equals(entity.getClass()))
			return false;

		@SuppressWarnings("unchecked")
		AbstractEntity<ID> e = (AbstractEntity<ID>) entity;

		return null == this.getId() ? false : this.getId().equals(e.getId());
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
	public abstract ID getId();

	/**
	 * @param id
	 *            da entitade
	 */
	public abstract void setId(ID id);

}
