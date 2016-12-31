/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import static br.com.cams7.app.validator.CepValidator.formatCep;
import static br.com.cams7.app.validator.CepValidator.unformatCep;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cams7.app.validator.CEP;

/**
 * @author César Magalhães
 *
 */
@Embeddable
public class Endereco {

	@NotEmpty
	@CEP
	@Column(unique = true, nullable = false, length = 8)
	private String cep;

	@NotEmpty
	@Size(min = 3, max = 60)
	@Column(nullable = false)
	private String bairro;

	@NotEmpty
	@Size(min = 3, max = 100)
	@Column(nullable = false)
	private String logradouro;

	@NotEmpty
	@Size(min = 1, max = 30)
	@Column(name = "numero_imovel", nullable = false)
	private String numeroImovel;

	@Column(name = "complemento_endereco", length = 30)
	private String complemento;

	@Column(name = "ponto_referencia", length = 100)
	private String pontoReferencia;

	/**
	 * @return CEP sem formatação
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            CEP sem formatação
	 * 
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return Nome do bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro
	 *            Nome do bairro
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return Nome do logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro
	 *            Nome do logradouro
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return Número do imóvel
	 */
	public String getNumeroImovel() {
		return numeroImovel;
	}

	/**
	 * @param numeroImovel
	 *            Número do imóvel
	 */
	public void setNumeroImovel(String numeroImovel) {
		this.numeroImovel = numeroImovel;
	}

	/**
	 * @return Informação adicional ao endereço
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento
	 *            Informação adicional ao endereço
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return Ponto de referência
	 */
	public String getPontoReferencia() {
		return pontoReferencia;
	}

	/**
	 * @param pontoReferencia
	 *            Ponto de referência
	 */
	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	/**
	 * @return CEP formatado
	 */
	public String getUnformattedCep() {
		if (cep == null || cep.isEmpty())
			return null;

		return unformatCep(cep);
	}

	/**
	 * @return CEP sem formatação
	 */
	public String getFormattedCep() {
		if (cep == null || cep.isEmpty())
			return null;

		return formatCep(cep);
	}

}
