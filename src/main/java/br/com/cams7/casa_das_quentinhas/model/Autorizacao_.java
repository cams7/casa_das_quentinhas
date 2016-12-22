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
@StaticMetamodel(Autorizacao.class)
public class Autorizacao_ {
	public static volatile SingularAttribute<Autorizacao, Integer> id;
	public static volatile SingularAttribute<Autorizacao, String> papel;
}
