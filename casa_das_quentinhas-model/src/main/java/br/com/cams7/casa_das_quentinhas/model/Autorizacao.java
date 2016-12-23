package br.com.cams7.casa_das_quentinhas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cams7.app.model.AbstractEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "autorizacao")
public class Autorizacao extends AbstractEntity<Integer> {

	@Id
	@SequenceGenerator(name = "id_autorizacao_seq", sequenceName = "id_autorizacao_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_autorizacao_seq")
	@Column(name = "id_autorizacao")
	private Integer id;

	@Column(length = 10, unique = true, nullable = false)
	private String papel = Papel.USER.getNome();

	@Override
	public Integer getId() {
		return id;
	}

	@Override
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
		final int PRIME = 31;
		int hashCode = super.hashCode();
		hashCode = PRIME * hashCode + ((papel == null) ? 0 : papel.hashCode());
		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		if (!super.equals(object))
			return false;

		Autorizacao autorizacao = (Autorizacao) object;
		if (papel == null) {
			if (autorizacao.papel != null)
				return false;
		} else if (!papel.equals(autorizacao.papel))
			return false;

		return true;
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
