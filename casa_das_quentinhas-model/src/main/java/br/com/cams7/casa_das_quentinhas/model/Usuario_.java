/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.cams7.casa_das_quentinhas.model.Usuario.Tipo;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Integer> id;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> senhaCriptografada;
	public static volatile SingularAttribute<Usuario, Tipo> tipo;
	public static volatile SingularAttribute<Usuario, Funcionario> funcionario;
}
