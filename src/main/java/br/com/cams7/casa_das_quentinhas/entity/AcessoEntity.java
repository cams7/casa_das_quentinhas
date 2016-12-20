package br.com.cams7.casa_das_quentinhas.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "acesso")
public class AcessoEntity implements Serializable {

	@Id
	@Column(length = 64)
	private String series;

	@Column(unique = true, length = 50, nullable = false)
	private String username;

	@Column(unique = true, length = 64, nullable = false)
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ultimo_acesso")
	private Date ultimoAcesso;

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
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
	public String toString() {
		return this.getClass().getSimpleName() + " [series=" + series + ", email=" + username + ", token=" + token
				+ ", ultimoAcesso=" + ultimoAcesso + "]";
	}

}
