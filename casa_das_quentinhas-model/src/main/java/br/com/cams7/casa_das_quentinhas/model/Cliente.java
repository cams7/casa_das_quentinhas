/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import static br.com.cams7.app.common.DateEditor.DATE_FORMAT;
import static br.com.cams7.app.validator.CpfValidator.formatCpf;
import static br.com.cams7.app.validator.CpfValidator.unformatCpf;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cams7.app.model.AbstractEntity;
import br.com.cams7.app.validator.CPF;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "cliente")
public class Cliente extends AbstractEntity<Integer> {

	@Id
	@SequenceGenerator(name = "cliente_id_seq", sequenceName = "cliente_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_id_seq")
	@Column(name = "id_cliente", nullable = false)
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

	@NotEmpty
	@Size(min = 3, max = 60)
	@Column(nullable = false)
	private String nome;

	@NotEmpty
	@CPF
	@Column(nullable = false, length = 11)
	private String cpf;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_contribuinte")
	private TipoContribuinte tipoContribuinte;

	@Temporal(TemporalType.DATE)
	@Column
	private Date nascimento;

	@Valid
	@Embedded
	private Contato contato;

	@Valid
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "numeroImovel", column = @Column(name = "numero_residencial")) })
	private Endereco endereco;

	@Embedded
	private Manutencao manutencao;

	@ManyToMany(mappedBy = "clientes", fetch = FetchType.LAZY)
	private List<Pedido> pedidos;

	/**
	 * 
	 */
	public Cliente() {
		super();
	}

	/**
	 * @param id
	 */
	public Cliente(Integer id) {
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
	 * @return Cidade onde mora o cliente
	 */
	public Cidade getCidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            Cidade onde mora o cliente
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
	 * @return Usuário que cadastrou o cliente
	 */
	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	/**
	 * @param usuarioCadastro
	 *            Usuário que cadastrou o cliente
	 */
	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	/**
	 * @return Nome do cliente
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Nome do cliente
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return CPF do cliente sem formatação
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            CPF do cliente sem formatação
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return Tipo de contribuinte
	 */
	public TipoContribuinte getTipoContribuinte() {
		return tipoContribuinte;
	}

	/**
	 * @param tipoContribuinte
	 *            Tipo de contribuinte
	 */
	public void setTipoContribuinte(TipoContribuinte tipoContribuinte) {
		this.tipoContribuinte = tipoContribuinte;
	}

	/**
	 * @return Data de nascimento
	 */
	public Date getNascimento() {
		return nascimento;
	}

	/**
	 * @param nascimento
	 *            Data de nascimento
	 */
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	/**
	 * @return Contato do cliente
	 */
	public Contato getContato() {
		return contato;
	}

	/**
	 * @param contato
	 *            Contato do cliente
	 */
	public void setContato(Contato contato) {
		this.contato = contato;
	}

	/**
	 * @return Endereço do cliente
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            Endereço do cliente
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
	 * @return Pedidos do cliente
	 */
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	/**
	 * @param pedidos
	 *            Pedidos do cliente
	 */
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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

	public String getFormattedNascimento() {
		if (nascimento == null)
			return null;

		String formattedNascimento = DATE_FORMAT.format(nascimento);
		return formattedNascimento;
	}

	/**
	 * @return Nome com o CPF formatado
	 */
	public String getNomeWithCpf() {
		return getNomeWithCpf(nome, cpf);
	}

	/**
	 * @param nome
	 *            Nome do cliente
	 * @param cpf
	 *            CPF do cliente
	 * @return Nome com o CPF formatado
	 */
	public static String getNomeWithCpf(String nome, String cpf) {
		if (nome == null || cpf == null)
			return null;

		return nome + " < " + formatCpf(cpf) + " >";
	}

	/**
	 * @param tipoContribuinte
	 *            Tipo de contribuinte
	 * @return Código do tipo de contribuinte
	 */
	public static Byte getCodigoTipoContribuinte(TipoContribuinte tipoContribuinte) {
		switch (tipoContribuinte) {
		case CONTRIBUINTE_ICMS:
			return 1;
		case CONTRIBUINTE_ISENTO:
			return 2;
		case NAO_CONTRIBUINTE:
			return 9;
		default:
			break;
		}

		return null;
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
		hashCode = PRIME * hashCode + ((cpf == null) ? 0 : cpf.hashCode());
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

		Cliente cliente = (Cliente) object;
		if (cpf == null) {
			if (cliente.cpf != null)
				return false;
		} else if (!cpf.equals(cliente.cpf))
			return false;

		return true;
	}

	/**
	 * @author César Magalhães
	 *
	 *         Tipo de contribuinte (1 - Contribuinte ICMS, 2 - Contribuinte
	 *         ISENTO, 9 - Não contribuinte)
	 */
	public enum TipoContribuinte {
		CONTRIBUINTE_ICMS("Contribuinte ICMS"), CONTRIBUINTE_ISENTO("Contribuinte ISENTO"), NAO_CONTRIBUINTE(
				"Não contribuinte");
		private String descricao;

		private TipoContribuinte(String descricao) {
			this.descricao = descricao;
		}

		public TipoContribuinte getTipoContribuinte() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

}
