package br.com.cams7.casa_das_quentinhas.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {

	@Id
	@SequenceGenerator(name = "id_usuario_seq", sequenceName = "id_usuario_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_usuario_seq")
	@Column(name = "id_usuario")
	private Integer id;

	@NotEmpty
	@Size(min = 5, max = 50)
	@Column(nullable = false)
	private String email;

	@NotEmpty
	@Column(length = 100, nullable = false)
	private String senha;

	@NotEmpty
	@Size(min = 3, max = 30)
	@Column(nullable = false)
	private String nome;

	@NotEmpty
	@Size(min = 3, max = 30)
	@Column(nullable = false)
	private String sobrenome;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_autorizacao", joinColumns = { @JoinColumn(name = "id_usuario") }, inverseJoinColumns = {
			@JoinColumn(name = "id_autorizacao") })
	private Set<AutorizacaoEntity> autorizacoes = new HashSet<AutorizacaoEntity>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Set<AutorizacaoEntity> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(Set<AutorizacaoEntity> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UsuarioEntity))
			return false;
		UsuarioEntity other = (UsuarioEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	/*
	 * DO-NOT-INCLUDE passwords in toString function. It is done here just for
	 * convenience purpose.
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [id=" + id + ", email=" + email + ", senha=" + senha + ", nome="
				+ nome + ", sobrenome=" + sobrenome + "]";
	}

}
