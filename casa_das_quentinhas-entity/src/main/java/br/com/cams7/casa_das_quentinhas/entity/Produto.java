/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import static br.com.cams7.app.common.MoneyEditor.NUMBER_FORMAT;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.cams7.app.entity.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "produto")
public class Produto extends AbstractEntity<Integer> {

	@Id
	@SequenceGenerator(name = "produto_id_seq", sequenceName = "produto_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_id_seq")
	@Column(name = "id_produto", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_usuario_cadastro", referencedColumnName = "id_usuario")
	private Usuario usuarioCadastro;

	@NotEmpty
	@Size(min = 3, max = 60)
	@Column(nullable = false)
	private String nome;

	@NotEmpty
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	@Column(nullable = false)
	private String ingredientes;

	@NotNull
	@Column
	private Float custo;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column
	private Tamanho tamanho;

	@Column(name = "codigo_ncm", length = 8)
	private String codigoNcm;

	@Column(name = "codgio_cest", length = 7)
	private String codigoCest;

	@Embedded
	private Manutencao manutencao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produto")
	private List<PedidoItem> itens;

	/**
	 * 
	 */
	public Produto() {
		super();
	}

	/**
	 * @param id
	 */
	public Produto(Integer id) {
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
	 * @return Usuário que cadastrou o produto
	 */
	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	/**
	 * @param usuarioCadastro
	 *            Usuário que cadastrou o produto
	 */
	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	/**
	 * @return Nome do produto
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Nome do produto
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return Ingredientes do produto
	 */
	public String getIngredientes() {
		return ingredientes;
	}

	/**
	 * @param ingredientes
	 *            Ingredientes do produto
	 */
	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}

	/**
	 * @return Custo do produto
	 */
	public Float getCusto() {
		return custo;
	}

	/**
	 * @param custo
	 *            Custo do produto
	 */
	public void setCusto(Float custo) {
		this.custo = custo;
	}

	/**
	 * @return Tamanho do produto
	 */
	public Tamanho getTamanho() {
		return tamanho;
	}

	/**
	 * @param tamanho
	 *            Tamanho do produto
	 */
	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	/**
	 * @return Nomenclatura Comum do Mercosul - NCM
	 */
	public String getCodigoNcm() {
		return codigoNcm;
	}

	/**
	 * @param codigoNcm
	 *            Nomenclatura Comum do Mercosul - NCM
	 */
	public void setCodigoNcm(String codigoNcm) {
		this.codigoNcm = codigoNcm;
	}

	/**
	 * @return Código Especificador da Substituição Tributária - CEST
	 */
	public String getCodigoCest() {
		return codigoCest;
	}

	/**
	 * @param codigoCest
	 *            Código Especificador da Substituição Tributária - CEST
	 */
	public void setCodigoCest(String codigoCest) {
		this.codigoCest = codigoCest;
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
	 * @return Itens do pedido
	 */
	public List<PedidoItem> getItens() {
		return itens;
	}

	/**
	 * @param itens
	 *            Itens do pedido
	 */
	public void setItens(List<PedidoItem> itens) {
		this.itens = itens;
	}

	public String getFormattedCusto() {
		if (custo == null)
			return null;

		return NUMBER_FORMAT.format(custo);
	}

	/**
	 * @return Nome com tamanho
	 */
	public String getNomeWithTamanho() {
		return getNomeWithTamanho(nome, tamanho);
	}

	/**
	 * @param nome
	 *            Nome do produto
	 * @param tamanho
	 *            Tamanho do produto
	 * @return Nome com tamanho
	 */
	public static String getNomeWithTamanho(String nome, Tamanho tamanho) {
		if (nome == null || tamanho == null)
			return null;

		return nome + " < " + tamanho.getDescricao() + " >";
	}

	/**
	 * @author César Magalhães
	 *
	 *         Tamanho do produto
	 */
	public enum Tamanho {
		GRANDE("Grande"), MEDIO("Médio"), PEQUENO("Pequeno");
		private String descricao;

		private Tamanho(String descricao) {
			this.descricao = descricao;
		}

		public Tamanho getTamanho() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

}
