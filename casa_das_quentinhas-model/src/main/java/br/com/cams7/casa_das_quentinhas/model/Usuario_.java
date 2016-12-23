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
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Integer> id;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> senhaCriptografada;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, String> sobrenome;
	public static volatile SetAttribute<Usuario, Autorizacao> autorizacoes;
}