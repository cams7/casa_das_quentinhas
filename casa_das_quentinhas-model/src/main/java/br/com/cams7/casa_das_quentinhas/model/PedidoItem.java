/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.cams7.app.model.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "pedido_item")
public class PedidoItem extends AbstractEntity<PedidoItemPK> {

	@EmbeddedId
	private PedidoItemPK id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido", insertable = false, updatable = false)
	private Pedido pedido;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_produto", referencedColumnName = "id_produto", insertable = false, updatable = false)
	private Produto produto;

	@NotNull
	@Column
	private Short quantidade;

	@Column(name = "codigo_cfop", length = 5)
	private String codigoCfop;

	@Column(name = "codigo_icms", length = 6)
	private String codigoIcms;

	@Column(name = "codigo_icms_origem")
	private Character codigoIcmsOrigem;

	@Column(name = "codigo_pis", length = 2)
	private String codigoPis;

	@Column(name = "codigo_cofins", length = 2)
	private String codigoCofins;

	@Column(name = "codigo_csosn", length = 3)
	private String codigoCsosn;

	/**
	 * 
	 */
	public PedidoItem() {
		super();
	}

	/**
	 * @param id
	 */
	public PedidoItem(PedidoItemPK id) {
		super(id);
	}

	/**
	 * @param pedidoId
	 * @param produtoId
	 */
	public PedidoItem(Long pedidoId, Integer produtoId) {
		this(new PedidoItemPK(pedidoId, produtoId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#getId()
	 */
	@Override
	public PedidoItemPK getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(PedidoItemPK id) {
		this.id = id;
	}

	/**
	 * @return Pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * @param pedido
	 *            Pedido
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * @return Produto
	 */
	public Produto getProduto() {
		return produto;
	}

	/**
	 * @param produto
	 *            Produto
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	/**
	 * @return Quantidade de produtos
	 */
	public Short getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade
	 *            Quantidade de produtos
	 */
	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return Código Fiscal de Operações e Prestação - CFOP
	 */
	public String getCodigoCfop() {
		return codigoCfop;
	}

	/**
	 * @param codigoCfop
	 *            Código Fiscal de Operações e Prestação - CFOP
	 */
	public void setCodigoCfop(String codigoCfop) {
		this.codigoCfop = codigoCfop;
	}

	/**
	 * @return ICMS - IMPOSTO SOBRE CIRCULAÇÃO DE MERCADORIAS E PRESTAÇÃO DE
	 *         SERVIÇOS
	 */
	public String getCodigoIcms() {
		return codigoIcms;
	}

	/**
	 * @param codigoIcms
	 *            ICMS - IMPOSTO SOBRE CIRCULAÇÃO DE MERCADORIAS E PRESTAÇÃO DE
	 *            SERVIÇOS
	 */
	public void setCodigoIcms(String codigoIcms) {
		this.codigoIcms = codigoIcms;
	}

	/**
	 * @return ICMS
	 */
	public Character getCodigoIcmsOrigem() {
		return codigoIcmsOrigem;
	}

	/**
	 * @param codigoIcmsOrigem
	 *            ICMS
	 */
	public void setCodigoIcmsOrigem(Character codigoIcmsOrigem) {
		this.codigoIcmsOrigem = codigoIcmsOrigem;
	}

	/**
	 * @return Programa Integração Social - PIS
	 */
	public String getCodigoPis() {
		return codigoPis;
	}

	/**
	 * @param codigoPis
	 *            Programa Integração Social - PIS
	 */
	public void setCodigoPis(String codigoPis) {
		this.codigoPis = codigoPis;
	}

	/**
	 * @return Contribuição para o Financiamento da Seguridade Social - COFINS
	 */
	public String getCodigoCofins() {
		return codigoCofins;
	}

	/**
	 * @param codigoCofins
	 *            Contribuição para o Financiamento da Seguridade Social -
	 *            COFINS
	 */
	public void setCodigoCofins(String codigoCofins) {
		this.codigoCofins = codigoCofins;
	}

	/**
	 * @return Codigo de Situação da Operação no Simples Nacional - CSOSN
	 */
	public String getCodigoCsosn() {
		return codigoCsosn;
	}

	/**
	 * @param codigoCsosn
	 *            Codigo de Situação da Operação no Simples Nacional - CSOSN
	 */
	public void setCodigoCsosn(String codigoCsosn) {
		this.codigoCsosn = codigoCsosn;
	}

}
