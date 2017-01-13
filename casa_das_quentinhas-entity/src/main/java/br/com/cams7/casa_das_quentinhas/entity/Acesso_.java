/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Acesso.class)
public class Acesso_ {
	public static volatile SingularAttribute<Acesso, String> id;
	public static volatile SingularAttribute<Acesso, String> email;
	public static volatile SingularAttribute<Acesso, String> token;
	public static volatile SingularAttribute<Acesso, Date> ultimoAcesso;
}
