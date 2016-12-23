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
	@Column(name = "series", length = 64)
	private String id;

	@Column(unique = true, length = 50, nullable = false)
	private String username;

	@Column(unique = true, length = 64, nullable = false)
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ultimo_acesso")
	private Date ultimoAcesso;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hashCode = super.hashCode();
		hashCode = PRIME * hashCode + ((username == null) ? 0 : username.hashCode());
		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		if (!super.equals(object))
			return false;

		Acesso acesso = (Acesso) object;
		if (username == null) {
			if (acesso.username != null)
				return false;
		} else if (!username.equals(acesso.username))
			return false;

		return true;
	}

}
