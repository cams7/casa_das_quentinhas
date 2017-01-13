/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import static br.com.cams7.app.validator.CnpjValidator.formatCnpj;
import static br.com.cams7.app.validator.CnpjValidator.unformatCnpj;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cams7.app.entity.AbstractEntity;
import br.com.cams7.app.validator.CNPJ;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "empresa")
public class Empresa extends AbstractEntity<Integer> {

	@Id
	@SequenceGenerator(name = "empresa_id_seq", sequenceName = "empresa_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_id_seq")
	@Column(name = "id_empresa", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade")
	private Cidade cidade;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_acesso", referencedColumnName = "id_usuario")
	private Usuario usuarioAcesso;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_usuario_cadastro", referencedColumnName = "id_usuario")
	private Usuario usuarioCadastro;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_empresa")
	private Tipo tipo;

	@NotEmpty
	@Size(min = 3, max = 60)
	@Column(name = "razao_social", nullable = false)
	private String razaoSocial;

	@NotEmpty
	@Size(min = 3, max = 60)
	@Column(name = "nome_fantasia", nullable = false)
	private String nomeFantasia;

	@NotEmpty
	@CNPJ
	@Column(unique = true, nullable = false, length = 14)
	private String cnpj;

	@Column(name = "inscricao_estadual", length = 15)
	private String inscricaoEstadual;

	@Column(name = "inscricao_estadual_st", length = 15)
	private String inscricaoEstadualST;

	@Column(name = "inscricao_muncipal", length = 20)
	private String inscricaoMuncipal;

	@Column(name = "codigo_cnae", length = 9)
	private String codigoCnae;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "regime_tributario")
	private RegimeTributario regimeTributario;

	@Valid
	@Embedded
	private Contato contato;

	@Valid
	@Embedded
	private Endereco endereco;

	@Embedded
	private Manutencao manutencao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	private List<Funcionario> funcionarios;

	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
	private List<Pedido> pedidos;

	/**
	 * 
	 */
	public Empresa() {
		super();
	}

	/**
	 * @param id
	 */
	public Empresa(Integer id) {
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
	 * @return Cidade onde está localizada a empresa
	 */
	public Cidade getCidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            Cidade onde está localizada a empresa
	 * 
	 */
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return Usuário de acesso ao sistema
	 */
	public Usuario getUsuarioAcesso() {
		return usuarioAcesso;
	}

	/**
	 * @param usuarioAcesso
	 *            Usuário de acesso ao sistema
	 */
	public void setUsuarioAcesso(Usuario usuarioAcesso) {
		this.usuarioAcesso = usuarioAcesso;
	}

	/**
	 * @return Usuário que cadastrou a empresa
	 */
	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	/**
	 * @param usuarioCadastro
	 *            Usuário que cadastrou a empresa
	 */
	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	/**
	 * @return Tipo de empresa
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            Tipo de empresa
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Razão social da empresa
	 */
	public String getRazaoSocial() {
		return razaoSocial;
	}

	/**
	 * @param razaoSocial
	 *            Razão social da empresa
	 */
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	/**
	 * @return Nome fantasia da empresa
	 */
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	/**
	 * @param nomeFantasia
	 *            Nome fantasia da empresa
	 */
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	/**
	 * @return CNPJ da empresa sem formatação
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * @param cnpj
	 *            CNPJ da empresa sem formatação
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @return Inscrição estadual sem formatação
	 */
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	/**
	 * @param inscricaoEstadual
	 *            Inscrição estadual sem formatação
	 */
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	/**
	 * @return Inscrição estadual do subst. tributário sem formatação
	 */
	public String getInscricaoEstadualST() {
		return inscricaoEstadualST;
	}

	/**
	 * @param inscricaoEstadualST
	 *            Inscrição estadual do subst. tributário sem formatação
	 */
	public void setInscricaoEstadualST(String inscricaoEstadualST) {
		this.inscricaoEstadualST = inscricaoEstadualST;
	}

	/**
	 * @return Inscrição municipal sem formatação
	 */
	public String getInscricaoMuncipal() {
		return inscricaoMuncipal;
	}

	/**
	 * @param inscricaoMuncipal
	 *            Inscrição municipal sem formatação
	 */
	public void setInscricaoMuncipal(String inscricaoMuncipal) {
		this.inscricaoMuncipal = inscricaoMuncipal;
	}

	/**
	 * @return the Códido CNAE sem formatação
	 */
	public String getCodigoCnae() {
		return codigoCnae;
	}

	/**
	 * @param codigoCnae
	 *            Códido CNAE sem formatação
	 */
	public void setCodigoCnae(String codigoCnae) {
		this.codigoCnae = codigoCnae;
	}

	/**
	 * @return Regime tributário (Simples Nacional, Simples Nacional - excesso
	 *         de sublimite de receita bruta, Regime Normal)
	 */
	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	/**
	 * @param regimeTributario
	 *            Regime tributário
	 */
	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	/**
	 * @return Contato da empresa
	 */
	public Contato getContato() {
		return contato;
	}

	/**
	 * @param contato
	 *            Contato da empresa
	 */
	public void setContato(Contato contato) {
		this.contato = contato;
	}

	/**
	 * @return Endereço da empresa
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            Endereço da empresa
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return Data de cadastro e alteração
	 */
	public Manutencao getManutencao() {
		return manutencao;
	}

	/**
	 * @param manutencao
	 *            Data de cadastro e alteração
	 */
	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}

	/**
	 * @return Funcionários da empresa
	 */
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	/**
	 * @param funcionarios
	 *            Funcionários da empresa
	 */
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	/**
	 * @return Pedidos da empresa
	 */
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	/**
	 * @param pedidos
	 *            Pedidos da empresa
	 */
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	/**
	 * @return CNPJ formatado
	 */
	public String getUnformattedCnpj() {
		if (cnpj == null || cnpj.isEmpty())
			return null;

		return unformatCnpj(cnpj);
	}

	/**
	 * @return CNPJ sem formatação
	 */
	public String getFormattedCnpj() {
		if (cnpj == null || cnpj.isEmpty())
			return null;

		return formatCnpj(cnpj);
	}

	/**
	 * @return Razão social com o CNPJ formatado
	 */
	public String getRazaoSocialWithCnpj() {
		return getRazaoSocialWithCnpj(razaoSocial, cnpj);
	}

	/**
	 * @param razaoSocial
	 *            Razão social da empresa
	 * @param cnpj
	 *            CNPJ da empresa
	 * @return Razão social com o CNPJ formatado
	 */
	public static String getRazaoSocialWithCnpj(String razaoSocial, String cnpj) {
		if (razaoSocial == null || cnpj == null)
			return null;

		return razaoSocial + " < " + formatCnpj(cnpj) + " >";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hashCode = super.hashCode();
		hashCode = PRIME * hashCode + ((cnpj == null) ? 0 : cnpj.hashCode());
		return hashCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!super.equals(object))
			return false;

		Empresa empresa = (Empresa) object;
		if (cnpj == null) {
			if (empresa.cnpj != null)
				return false;
		} else if (!cnpj.equals(empresa.cnpj))
			return false;

		return true;
	}

	/**
	 * @author César Magalhães
	 *
	 *         Tipo de Empresa
	 */
	public enum Tipo {
		CLIENTE("Cliente"), ENTREGA("Entrega");
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

	/**
	 * @author César Magalhães
	 *
	 *         Regime tributário
	 */
	public enum RegimeTributario {
		SIMPLES_NACIONAL("Simples Nacional"), SUBLIMITE_RECEITA(
				"Simples Nacional - excesso de sublimite de receita bruta"), REGIME_NORMAL("Regime Normal");
		private String descricao;

		private RegimeTributario(String descricao) {
			this.descricao = descricao;
		}

		public RegimeTributario getRegimeTributario() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

	/**
	 * @author César Magalhães
	 *
	 *         Possiveis relações da empresa
	 */
	public enum RelacionamentoEmpresa {
		FUNCIONARIOS, PEDIDOS
	}

}
