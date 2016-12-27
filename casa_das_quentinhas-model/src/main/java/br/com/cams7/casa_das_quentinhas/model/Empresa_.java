/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Empresa.class)
public class Empresa_ {
	public static volatile SingularAttribute<Empresa, Integer> id;
	public static volatile SingularAttribute<Empresa, String> nome;
	public static volatile SingularAttribute<Empresa, String> cnpj;
	public static volatile SetAttribute<Empresa, Funcionario> funcionarios;
}
