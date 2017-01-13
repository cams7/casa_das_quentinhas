/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author César Magalhães
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PedidoItemPK implements Serializable {

	@Column(name = "id_pedido", nullable = false)
	private Long pedidoId;

	@Column(name = "id_produto", nullable = false)
	private Integer produtoId;

	/**
	 * 
	 */
	public PedidoItemPK() {
		super();
	}

	/**
	 * @param pedidoId
	 * @param produtoId
	 */
	public PedidoItemPK(Long pedidoId, Integer produtoId) {
		this();

		this.pedidoId = pedidoId;
		this.produtoId = produtoId;
	}

	/**
	 * @return ID do pedido
	 */
	public Long getPedidoId() {
		return pedidoId;
	}

	/**
	 * @param pedidoId
	 *            ID do pedido
	 */
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	/**
	 * @return ID do produto
	 */
	public Integer getProdutoId() {
		return produtoId;
	}

	/**
	 * @param produtoId
	 *            ID do produto
	 */
	/**
	 * @param produtoId
	 */
	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	@Override
	public String toString() {
		return String.format("%s{pedidoId:%s, produtoId:%s}", this.getClass().getSimpleName(), pedidoId, produtoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {

		if (null == object)
			return false;

		if (this == object)
			return true;

		if (!getClass().equals(object.getClass()))
			return false;

		PedidoItemPK pk = (PedidoItemPK) object;

		if (pedidoId == null) {
			if (pk.pedidoId != null)
				return false;
		} else if (!pedidoId.equals(pk.pedidoId))
			return false;

		if (produtoId == null) {
			if (pk.produtoId != null)
				return false;
		} else if (!produtoId.equals(pk.produtoId))
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;

		int hashCode = 1;
		hashCode = PRIME * hashCode + ((pedidoId == null) ? 0 : pedidoId.hashCode());
		hashCode = PRIME * hashCode + ((produtoId == null) ? 0 : produtoId.hashCode());

		return hashCode;
	}

}
