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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cams7.app.model.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "empresa")
public class Empresa extends AbstractEntity<Integer> {

	@Id
	@SequenceGenerator(name = "empresa_id_seq", sequenceName = "empresa_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_id_seq")
	@Column(name = "id_empresa", nullable = false)
	private Integer id;

	@NotEmpty
	@Size(min = 3, max = 60)
	@Column(name = "razao_social", nullable = false)
	private String nome;

	@NotEmpty
	// @CNPJ
	@Column(nullable = false, length = 14)
	private String cnpj;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	private Set<Funcionario> funcionarios;

	public Empresa() {
		super();
	}

	public Empresa(Integer id) {
		super(id);
	}

	public Empresa(Integer id, String nome, String cnpj) {
		this(id);

		this.nome = nome;
		this.cnpj = cnpj;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Set<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public String getNomeWithCnpj() {
		if (getNome() == null || getCnpj() == null)
			return null;

		return getNome() + " < " + getCnpj().replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5")
				+ " >";
	}

}
