package br.com.cams7.casa_das_quentinhas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "autorizacao")
public class AutorizacaoEntity implements Serializable {

	@Id
	@SequenceGenerator(name = "id_autorizacao_seq", sequenceName = "id_autorizacao_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_autorizacao_seq")
	@Column(name = "id_autorizacao")
	private Integer id;

	@Column(length = 10, unique = true, nullable = false)
	private String papel = Papel.USER.getNome();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((papel == null) ? 0 : papel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AutorizacaoEntity))
			return false;
		AutorizacaoEntity other = (AutorizacaoEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (papel == null) {
			if (other.papel != null)
				return false;
		} else if (!papel.equals(other.papel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [id=" + id + ", papel=" + papel + "]";
	}

	public enum Papel implements Serializable {
		USER("USER"), DBA("DBA"), ADMIN("ADMIN");

		private String nome;

		private Papel(String nome) {
			this.nome = nome;
		}

		public String getNome() {
			return nome;
		}

	}

}
