package br.com.cams7.casa_das_quentinhas.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.cams7.app.model.AbstractEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "acesso")
public class Acesso extends AbstractEntity<String> {

	@Id
	@Column(name = "series", length = 64, nullable = false)
	private String id;

	@Column(unique = true, length = 50, nullable = false)
	private String email;

	@Column(unique = true, length = 64, nullable = false)
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ultimo_acesso", nullable = false)
	private Date ultimoAcesso;

	/**
	 * 
	 */
	public Acesso() {
		super();
	}

	/**
	 * @param id
	 */
	public Acesso(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return E-mail do usuário que logou no sistema
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            E-mail do usuário que logou no sistema
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            Token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return Data do último acesso
	 */
	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	/**
	 * @param ultimoAcesso
	 *            Data do último acesso
	 */
	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hashCode = super.hashCode();
		hashCode = PRIME * hashCode + ((email == null) ? 0 : email.hashCode());
		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		if (!super.equals(object))
			return false;

		Acesso acesso = (Acesso) object;
		if (email == null) {
			if (acesso.email != null)
				return false;
		} else if (!email.equals(acesso.email))
			return false;

		return true;
	}

}
