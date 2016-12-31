/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author César Magalhães
 *
 */
@Embeddable
public class Manutencao {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false)
	private Date cadastro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_alteracao", nullable = false)
	private Date alteracao;

	/**
	 * @return Data do cadastro
	 */
	public Date getCadastro() {
		return cadastro;
	}

	/**
	 * @param cadastro
	 *            Data do cadastro
	 */
	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	/**
	 * @return Data da alteração dos dados
	 */
	public Date getAlteracao() {
		return alteracao;
	}

	/**
	 * @param alteracao
	 *            Data da alteração dos dados
	 */
	public void setAlteracao(Date alteracao) {
		this.alteracao = alteracao;
	}
}
