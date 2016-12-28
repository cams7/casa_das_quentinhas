package br.com.cams7.casa_das_quentinhas.model;

import static br.com.cams7.app.validator.CelularValidator.formatCelular;
import static br.com.cams7.app.validator.CelularValidator.unformatCelular;
import static br.com.cams7.app.validator.CpfValidator.formatCpf;
import static br.com.cams7.app.validator.CpfValidator.unformatCpf;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cams7.app.model.AbstractEntity;
import br.com.cams7.app.validator.CPF;
import br.com.cams7.app.validator.Celular;
import br.com.cams7.app.validator.RG;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionario")
public class Funcionario extends AbstractEntity<Integer> {

	@Id
	@Column(name = "id_funcionario", nullable = false)
	private Integer id;

	@Valid
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@PrimaryKeyJoinColumn(name = "id_funcionario", referencedColumnName = "id_usuario")
	private Usuario usuario;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column
	private Funcao funcao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_cadastro", referencedColumnName = "id_usuario")
	private Usuario usuarioCadastro;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
	private Empresa empresa;

	@NotEmpty
	@Size(min = 3, max = 60)
	@Column(nullable = false)
	private String nome;

	@NotEmpty
	@CPF
	@Column(nullable = false, length = 11)
	private String cpf;

	@NotEmpty
	@RG
	@Column(nullable = false, length = 10)
	private String rg;

	@NotEmpty
	@Celular
	@Column(nullable = false, length = 11)
	private String celular;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false)
	private Date cadastro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_alteracao", nullable = false)
	private Date alteracao;

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
	 * @return Usuário de acesso ao sistema
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            Usuário de acesso ao sistema
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return Função do funcionário
	 */
	public Funcao getFuncao() {
		return funcao;
	}

	/**
	 * @param funcao
	 *            Função do funcionário
	 */
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	/**
	 * @return Usuário que cadastrou o funcionário
	 */
	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	/**
	 * @param usuarioCadastro
	 *            Usuário que cadastrou o funcionário
	 */
	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	/**
	 * @return Empresa a qual pertence o funcionário
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            Empresa a qual pertence o funcionário
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return Nome do funcionário
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Nome do funcionário
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return CPF do funcionário sem formatação
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            CPF do funcionário sem formatação
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return Número de identidade do funcionário sem formatação
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg
	 *            Número de identidade do funcionário sem formatação
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return Número de celular do funcionário sem formatação
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * @param celular
	 *            Número de celular do funcionário sem formatação
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return Data do cadastro do funcionário
	 */
	public Date getCadastro() {
		return cadastro;
	}

	/**
	 * @param cadastro
	 *            Data do cadastro do funcionário
	 */
	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	/**
	 * @return Data da alteração dos dados do funcionário
	 */
	public Date getAlteracao() {
		return alteracao;
	}

	/**
	 * @param alteracao
	 *            Data da alteração dos dados do funcionário
	 */
	public void setAlteracao(Date alteracao) {
		this.alteracao = alteracao;
	}

	/**
	 * @return CPF formatado
	 */
	public String getUnformattedCpf() {
		if (cpf == null || cpf.isEmpty())
			return null;

		return unformatCpf(cpf);
	}

	/**
	 * @return CPF sem formatação
	 */
	public String getFormattedCpf() {
		if (cpf == null || cpf.isEmpty())
			return null;

		return formatCpf(cpf);
	}

	/**
	 * @return Celular formatado
	 */
	public String getUnformattedCelular() {
		if (celular == null || celular.isEmpty())
			return null;

		return unformatCelular(celular);
	}

	/**
	 * @return Celular sem formatação
	 */
	public String getFormattedCelular() {
		if (celular == null || celular.isEmpty())
			return null;

		return formatCelular(celular);
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hashCode = super.hashCode();
		hashCode = PRIME * hashCode + ((cpf == null) ? 0 : cpf.hashCode());
		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		if (!super.equals(object))
			return false;

		Funcionario funcionario = (Funcionario) object;
		if (cpf == null) {
			if (funcionario.cpf != null)
				return false;
		} else if (!cpf.equals(funcionario.cpf))
			return false;

		return true;
	}

	/**
	 * @author César Magalhães
	 * 
	 *         Função do Funcionário
	 */
	public enum Funcao {
		GERENTE("Gerente"), ATENDENTE("Atendente"), ENTREGADOR("Entregador(a)");
		private String descricao;

		private Funcao(String descricao) {
			this.descricao = descricao;
		}

		public Funcao getFuncao() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

}
