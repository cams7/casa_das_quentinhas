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

import org.hibernate.validator.constraints.Email;
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
	@Email
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

	/**
	 * 
	 */
	public Usuario() {
		super();
	}

	/**
	 * @param id
	 */
	public Usuario(Integer id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#getId()
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return E-mail de acesso
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            E-mail de acesso
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Senha criptografada
	 */
	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}

	/**
	 * @param senhaCriptografada
	 *            Senha criptografada
	 */
	public void setSenhaCriptografada(String senhaCriptografada) {
		this.senhaCriptografada = senhaCriptografada;
	}

	/**
	 * @return Tipo de usuário
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            Tipo de usuário
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Funcionário que tem o mesmo id do usuário
	 */
	public Funcionario getFuncionario() {
		return funcionario;
	}

	/**
	 * @param funcionario
	 *            Funcionário
	 */
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	/**
	 * @return Funcionários cadastrados pelo usuário
	 */
	public Set<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	/**
	 * @param funcionarios
	 *            Funcionários
	 */
	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	/**
	 * @return Senha sem criptografia
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha
	 *            Senha sem criptografia
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return Senha de confirmação
	 */
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	/**
	 * @param confirmacaoSenha
	 *            Senha de confirmação
	 */
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

	/**
	 * @author César Magalhães
	 * 
	 *         Tipo de Usuário
	 */
	public enum Tipo {
		FUNCIONARIO("Funcionário(a)"), EMPRESA("Empresa");
		private String descricao;

		private Tipo(String descricao) {
			this.descricao = descricao;
		}

		public Tipo getTipo() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

}
