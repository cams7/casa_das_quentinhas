/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import static br.com.cams7.app.validator.TelefoneValidator.formatTelefone;
import static br.com.cams7.app.validator.TelefoneValidator.unformatTelefone;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.cams7.app.validator.Telefone;

/**
 * @author César Magalhães
 *
 */
@Embeddable
public class Contato {

	@NotEmpty
	@Size(min = 5, max = 50)
	@Email
	@Column(unique = true, nullable = false)
	private String email;

	@NotEmpty
	@Telefone
	@Column(nullable = false, length = 11)
	private String telefone;

	/**
	 * @return E-mail da empresa
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            E-mail da empresa
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Número de telefone da empresa sem formatação
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            Número de telefone da empresa sem formatação
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return Telefone formatado
	 */
	public String getUnformattedTelefone() {
		if (telefone == null || telefone.isEmpty())
			return null;

		return unformatTelefone(telefone);
	}

	/**
	 * @return Telefone sem formatação
	 */
	public String getFormattedTelefone() {
		if (telefone == null || telefone.isEmpty())
			return null;

		return formatTelefone(telefone);
	}
}
