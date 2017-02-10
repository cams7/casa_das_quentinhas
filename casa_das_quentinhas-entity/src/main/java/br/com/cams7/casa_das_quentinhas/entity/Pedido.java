/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import static br.com.cams7.app.common.MoneyEditor.NUMBER_FORMAT;

import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.cams7.app.entity.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "pedido")
public class Pedido extends AbstractEntity<Long> {

	@Id
	@SequenceGenerator(name = "pedido_id_seq", sequenceName = "pedido_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_id_seq")
	@Column(name = "id_pedido", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_usuario_cadastro", referencedColumnName = "id_usuario")
	private Usuario usuarioCadastro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_entregador", referencedColumnName = "id_funcionario")
	private Funcionario entregador;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_cliente")
	private TipoCliente tipoCliente;

	@NotNull
	@Column(name = "quantidade_total")
	private Short quantidade;

	@NotNull
	@Column(name = "total_nota")
	private Float custo;

	@Column(name = "icms_nota")
	private Float custoIcms;

	@Column(name = "st_nota")
	private Float custoSt;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "forma_pagamento")
	private FormaPagamento formaPagamento;

	@Column(name = "consumidor_final")
	private Boolean consumidorFinal;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "destino_operacao")
	private DestinoOperacao destinoOperacao;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_atendimento")
	private TipoAtendimento tipoAtendimento;

	@Size(min = 3, max = 20)
	@Column(name = "natureza_operacao")
	private String naturezaOperacao;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "situacao_pedido")
	private Situacao situacao;

	@Embedded
	private Manutencao manutencao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "empresa_pedido", joinColumns = { @JoinColumn(name = "id_pedido") }, inverseJoinColumns = {
			@JoinColumn(name = "id_empresa") })
	private Empresa empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "cliente_pedido", joinColumns = { @JoinColumn(name = "id_pedido") }, inverseJoinColumns = {
			@JoinColumn(name = "id_cliente") })
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido")
	private List<PedidoItem> itens;

	/**
	 * 
	 */
	public Pedido() {
		super();
	}

	/**
	 * @param id
	 */
	public Pedido(Long id) {
		super(id);
	}

	/**
	 * @param quantidade
	 * @param custo
	 */
	public Pedido(Short quantidade, Float custo) {
		this();

		this.quantidade = quantidade;
		this.custo = custo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Usuário que cadastrou o pedido
	 */
	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	/**
	 * @param usuarioCadastro
	 *            Usuário que cadastrou o pedido
	 */
	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	/**
	 * @return Entregador reponsavél pela entrega do pedido
	 */
	public Funcionario getEntregador() {
		return entregador;
	}

	/**
	 * @param entregador
	 *            Entregador reponsavél pela entrega do pedido
	 */
	public void setEntregador(Funcionario entregador) {
		this.entregador = entregador;
	}

	/**
	 * @return Tipo de cliente
	 */
	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	/**
	 * @param tipoCliente
	 *            Tipo de cliente
	 */
	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	/**
	 * @return Quantidade total de produtos
	 */
	public Short getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade
	 *            Quantidade total de produtos
	 */
	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return Valor total da nota
	 */
	public Float getCusto() {
		return custo;
	}

	/**
	 * @param custo
	 *            Valor total da nota
	 */
	public void setCusto(Float custo) {
		this.custo = custo;
	}

	/**
	 * @return Valor ICMS da nota
	 */
	public Float getCustoIcms() {
		return custoIcms;
	}

	/**
	 * @param custoIcms
	 *            Valor ICMS da nota
	 */
	public void setCustoIcms(Float custoIcms) {
		this.custoIcms = custoIcms;
	}

	/**
	 * @return Valor ICMS ST nota
	 */
	public Float getCustoSt() {
		return custoSt;
	}

	/**
	 * @param custoSt
	 *            Valor ICMS ST nota
	 */
	public void setCustoSt(Float custoSt) {
		this.custoSt = custoSt;
	}

	/**
	 * @return Forma de pagamento
	 */
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	/**
	 * @param formaPagamento
	 *            Forma de pagamento
	 */
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	/**
	 * @return Consumidor final
	 */
	public Boolean getConsumidorFinal() {
		return consumidorFinal;
	}

	/**
	 * @param consumidorFinal
	 *            Consumidor final
	 */
	public void setConsumidorFinal(Boolean consumidorFinal) {
		this.consumidorFinal = consumidorFinal;
	}

	/**
	 * @return Destino da operação
	 */
	public DestinoOperacao getDestinoOperacao() {
		return destinoOperacao;
	}

	/**
	 * @param destinoOperacao
	 *            Destino da operação
	 */
	public void setDestinoOperacao(DestinoOperacao destinoOperacao) {
		this.destinoOperacao = destinoOperacao;
	}

	/**
	 * @return Tipo de atendimento
	 */
	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	/**
	 * @param tipoAtendimento
	 *            Tipo de atendimento
	 */
	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	/**
	 * @return Natureza da operação
	 */
	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}

	/**
	 * @param naturezaOperacao
	 *            Natureza da operação
	 */
	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	/**
	 * @return Situção do pedido
	 */
	public Situacao getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao
	 *            Situção do pedido
	 */
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
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
	 * @return Empresa
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            Empresa
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return Cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 *            Cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	/**
	 * @return
	 */
	public String getFormattedCusto() {
		if (custo == null)
			return null;

		return NUMBER_FORMAT.format(custo);
	}

	/**
	 * @return
	 */
	public String getFormattedCustoIcms() {
		if (custoIcms == null)
			return null;

		return NUMBER_FORMAT.format(custoIcms);
	}

	/**
	 * @return
	 */
	public String getFormattedCustoSt() {
		if (custoSt == null)
			return null;

		return NUMBER_FORMAT.format(custoSt);
	}

	/**
	 * @return ID com data de cadastro
	 */
	public String getIdWithCadastro() {
		return getIdWithCadastro(id, manutencao != null ? manutencao.getCadastro() : null);
	}

	/**
	 * @param id
	 *            ID do pedido
	 * @param cadastro
	 *            Data de cadastro do pedido
	 * @return ID com data de cadastro
	 */
	public static String getIdWithCadastro(Long id, Date cadastro) {
		if (id == null || cadastro == null)
			return null;

		return id + " < " + Manutencao.getFormattedCadastro(cadastro) + " >";
	}

	/**
	 * @param destinoOperacao
	 *            Destino da operação
	 * @return Código do destino da operação
	 */
	public static Byte getCodigoDestinoOperacao(DestinoOperacao destinoOperacao) {
		switch (destinoOperacao) {
		case OPERACAO_INTERNA:
			return 1;
		case OPERACAO_INTERESTADUAL:
			return 2;
		case OPERACAO_COM_EXTERIOR:
			return 3;
		default:
			break;
		}

		return null;
	}

	/**
	 * @param tipoAtendimento
	 *            Tipo de atendimento
	 * @return Código do tipo de atendimento
	 */
	public static Byte getCodigoTipoAtendimento(TipoAtendimento tipoAtendimento) {
		switch (tipoAtendimento) {
		case NAO_SE_APLICA:
			return 0;
		case OPERACAO_PRESENCIAL:
			return 1;
		case OPERACAO_NAO_PRESENCIAL:
			return 2;
		case TELEATENDIMENTO:
			return 3;
		case OUTROS:
			return 9;
		default:
			break;
		}

		return null;
	}

	/**
	 * @author César Magalhães
	 *
	 *         Forma de pagamento (0 - Pagamento à vista, 1 - Pagamento a prazo,
	 *         2 - Outros)
	 */
	public enum FormaPagamento {
		PAGAMENTO_A_VISTA("Pagamento à vista"), PAGAMENTO_A_PRAZO("Pagamento a prazo"), OUTROS("Outros");

		private String descricao;

		private FormaPagamento(String descricao) {
			this.descricao = descricao;
		}

		public FormaPagamento getFormaPagamento() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

	/**
	 * @author César Magalhães
	 *
	 *         Destino da operação (1 - operação interna, 2 - operação
	 *         interestadual, 3 - Operação com exterior)
	 */
	public enum DestinoOperacao {
		OPERACAO_INTERNA("Operação interna"), OPERACAO_INTERESTADUAL("Operação interestadual"), OPERACAO_COM_EXTERIOR(
				"Operação com exterior");

		private String descricao;

		private DestinoOperacao(String descricao) {
			this.descricao = descricao;
		}

		public DestinoOperacao getDestinoOperacao() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

	/**
	 * @author César Magalhães
	 *
	 *         Tipo atendimento (0 - Não se aplica, 1 - Operação presencial, 2 -
	 *         operação não presencial, 3 - Operação não presencial (pela
	 *         teleatendimento), 9 - Operação não presencial (OUTROS))
	 */
	public enum TipoAtendimento {
		NAO_SE_APLICA("Não se aplica"), OPERACAO_PRESENCIAL("Operação presencial"), OPERACAO_NAO_PRESENCIAL(
				"Operação não presencial"), TELEATENDIMENTO(
						"Operação não presencial (pela teleatendimento)"), OUTROS("Operação não presencial (OUTROS)");

		private String descricao;

		private TipoAtendimento(String descricao) {
			this.descricao = descricao;
		}

		public TipoAtendimento getTipoAtendimento() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

	/**
	 * @author César Magalhães
	 *
	 *         Situação do pedido
	 */
	public enum Situacao {
		PENDENTE("Pendente"), EM_TRANSITO("Em trânsito"), CANCELADO("Cancelado"), ENTREGUE("Entregue");

		private String descricao;

		private Situacao(String descricao) {
			this.descricao = descricao;
		}

		public Situacao getSituacao() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

	/**
	 * @author César Magalhães
	 *
	 *         Tipo de cliente
	 */
	public enum TipoCliente {
		PESSOA_FISICA("Pessoa física"), PESSOA_JURIDICA("Pessoa juridica");
		private String descricao;

		private TipoCliente(String descricao) {
			this.descricao = descricao;
		}

		public TipoCliente getTipoCliente() {
			return values()[ordinal()];
		}

		public String getDescricao() {
			return descricao;
		}
	}

}
