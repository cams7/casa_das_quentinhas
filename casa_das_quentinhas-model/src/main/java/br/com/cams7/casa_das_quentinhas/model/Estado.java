/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.cams7.app.model.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "uf")
public class Estado extends AbstractEntity<Short> {

	@Id
	@SequenceGenerator(name = "uf_id_seq", sequenceName = "uf_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uf_id_seq")
	@Column(name = "id_uf", nullable = false)
	private Short id;

	@Column(length = 20, nullable = false)
	private String nome;

	@Column(length = 2, unique = true, nullable = false)
	private String sigla;

	@Column(name = "codigo_ibge", length = 2, unique = true, nullable = false)
	private Byte codigoIbge;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estado")
	private Set<Cidade> cidades;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#getId()
	 */
	@Override
	public Short getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.model.AbstractEntity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Short id) {
		this.id = id;
	}

	/**
	 * @return Nome do estado
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Nome do estado
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return Sigla do estado
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla
	 *            Sigla do estado
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return Código do estado de acordo com o IBGE
	 */
	public Byte getCodigoIbge() {
		return codigoIbge;
	}

	/**
	 * @param codigoIbge
	 *            Código do estado de acordo com o IBGE
	 */
	public void setCodigoIbge(Byte codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	/**
	 * @return Cidades pertencente ao estado
	 */
	public Set<Cidade> getCidades() {
		return cidades;
	}

	/**
	 * @param cidades
	 *            Cidades
	 */
	public void setCidades(Set<Cidade> cidades) {
		this.cidades = cidades;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int hashCode = super.hashCode();
		hashCode = PRIME * hashCode + ((sigla == null) ? 0 : sigla.hashCode());
		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		if (!super.equals(object))
			return false;

		Estado estado = (Estado) object;
		if (sigla == null) {
			if (estado.sigla != null)
				return false;
		} else if (!sigla.equals(estado.sigla))
			return false;

		return true;
	}

}
