package br.com.cams7.casa_das_quentinhas.model;

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

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
	private Empresa empresa;

	@NotEmpty
	@Size(min = 3, max = 60)
	@Column(nullable = false)
	private String nome;

	// @Pattern(regexp = "^(\\d{3})\\.(\\d{3})\\.(\\d{3})\\-(\\d{2})$")
	@NotEmpty
	// @CPF
	@Column(nullable = false, length = 11)
	private String cpf;

	@NotEmpty
	// @RG
	@Column(nullable = false, length = 10)
	private String rg;

	// @Pattern(regexp = "^\\((\\d{2})\\) (\\d{5})\\-(\\d{4})$")
	@NotEmpty
	// @RG
	@Column(nullable = false, length = 11)
	private String celular;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false)
	private Date cadastro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_alteracao", nullable = false)
	private Date alteracao;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getCadastro() {
		return cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public Date getAlteracao() {
		return alteracao;
	}

	public void setAlteracao(Date alteracao) {
		this.alteracao = alteracao;
	}

	public String getUnformattedCpf() {
		if (getCpf() == null || getCpf().isEmpty())
			return null;

		String cpf = getCpf().replaceAll("^(\\d{3})\\.(\\d{3})\\.(\\d{3})\\-(\\d{2})$", "$1$2$3$4");
		return cpf;
	}

	public String getFormattedCpf() {
		if (getCpf() == null || getCpf().isEmpty())
			return null;

		String cpf = getCpf().replaceAll("^(\\d{3})(\\d{3})(\\d{3})(\\d{2})$", "$1.$2.$3-$4");
		return cpf;
	}

	public String getUnformattedCelular() {
		if (getCelular() == null || getCelular().isEmpty())
			return null;

		String cpf = getCelular().replaceAll("^\\((\\d{2})\\) (\\d{5})\\-(\\d{4})$", "$1$2$3");
		return cpf;
	}

	public String getFormattedCelular() {
		if (getCelular() == null || getCelular().isEmpty())
			return null;

		String cpf = getCelular().replaceAll("^(\\d{2})(\\d{5})(\\d{4})$", "($1) $2-$3");
		return cpf;
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

	public enum Funcao {
		GERENTE("Gerente"), ATENDENTE("Atendente"), ENTREGADOR("Entregador(a)");
		private String nome;

		private Funcao(String nome) {
			this.nome = nome;
		}

		public Funcao getFuncao() {
			return values()[ordinal()];
		}

		public String getNome() {
			return nome;
		}
	}

}
