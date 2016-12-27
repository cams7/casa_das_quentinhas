package br.com.cams7.casa_das_quentinhas.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cams7.app.model.AbstractEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuario")
public class Usuario extends AbstractEntity<Integer> {

	@Id
	@SequenceGenerator(name = "usuario_id_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
	@Column(name = "id_usuario", nullable = false)
	private Integer id;

	@NotEmpty
	@Size(min = 5, max = 50)
	@Column(unique = true, nullable = false)
	private String email;

	@Column(name = "senha", length = 100, nullable = false)
	private String senhaCriptografada;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_usuario", nullable = false)
	private Tipo tipo;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario")
	private Funcionario funcionario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioCadastro")
	private Set<Funcionario> funcionarios;

	@Transient
	private String senha;

	@Transient
	private String confirmacaoSenha;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}

	public void setSenhaCriptografada(String senhaCriptografada) {
		this.senhaCriptografada = senhaCriptografada;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Set<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
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

		Usuario usuario = (Usuario) object;
		if (email == null) {
			if (usuario.email != null)
				return false;
		} else if (!email.equals(usuario.email))
			return false;

		return true;
	}

	public enum Tipo {
		FUNCIONARIO("Funcionário(a)"), EMPRESA("Empresa");
		private String nome;

		private Tipo(String nome) {
			this.nome = nome;
		}

		public Tipo getTipo() {
			return values()[ordinal()];
		}

		public String getNome() {
			return nome;
		}
	}

}
