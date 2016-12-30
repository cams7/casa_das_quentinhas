/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cidade")
public class Cidade extends AbstractEntity<Integer> {

	@Id
	@SequenceGenerator(name = "cidade_id_seq", sequenceName = "cidade_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_id_seq")
	@Column(name = "id_cidade", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_uf", referencedColumnName = "id_uf")
	private Estado estado;

	@Column(length = 64, nullable = false)
	private String nome;

	@Column(name = "codigo_ibge", length = 7, unique = true, nullable = false)
	private Long codigoIbge;

	@Column(length = 2, nullable = false)
	private Byte ddd;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
	private List<Empresa> empresas;

	/**
	 * 
	 */
	public Cidade() {
		super();
	}

	/**
	 * @param id
	 */
	public Cidade(Integer id) {
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
	 * @return Estado o qual pertence a cidade
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            Estado o qual pertence a cidade
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/**
	 * @return Nome da cidade
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Nome da cidade
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return Código da cidade de acordo com o IBGE
	 */
	public Long getCodigoIbge() {
		return codigoIbge;
	}

	/**
	 * @param codigoIbge
	 *            Código da cidade de acordo com o IBGE
	 */
	public void setCodigoIbge(Long codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	/**
	 * @return DDD da cidade - Discagem direta a distância
	 */
	public Byte getDdd() {
		return ddd;
	}

	/**
	 * @param ddd
	 *            DDD da cidade
	 */
	public void setDdd(Byte ddd) {
		this.ddd = ddd;
	}

	/**
	 * @return Empresas da cidade
	 */
	public List<Empresa> getEmpresas() {
		return empresas;
	}

	/**
	 * @param empresas
	 *            Empresas
	 */
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	/**
	 * @return Nome da cidade com a sigla do estado
	 */
	public String getNomeWithEstadoSigla() {
		if (estado == null)
			return null;

		return getNomeWithEstadoSigla(nome, estado.getSigla());
	}

	/**
	 * @param nome
	 *            Nome da cidade
	 * @param sigla
	 *            Sigla do estado
	 * @return Nome da cidade com a sigla do estado
	 */
	public static String getNomeWithEstadoSigla(String nome, String sigla) {
		if (nome == null || sigla == null)
			return null;

		return nome + " < " + sigla + " >";
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
		hashCode = PRIME * hashCode + ((codigoIbge == null) ? 0 : codigoIbge.hashCode());
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

		Cidade cidade = (Cidade) object;
		if (codigoIbge == null) {
			if (cidade.codigoIbge != null)
				return false;
		} else if (!codigoIbge.equals(cidade.codigoIbge))
			return false;

		return true;
	}

}
