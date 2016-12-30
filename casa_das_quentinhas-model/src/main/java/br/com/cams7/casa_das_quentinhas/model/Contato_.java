/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Contato.class)
public class Contato_ {
	public static volatile SingularAttribute<Contato, String> email;
	public static volatile SingularAttribute<Contato, String> telefone;
}
