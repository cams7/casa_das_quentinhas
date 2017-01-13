/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Cidade.class)
public class Cidade_ {
	public static volatile SingularAttribute<Cidade, Integer> id;
	public static volatile SingularAttribute<Cidade, Estado> estado;
	public static volatile SingularAttribute<Cidade, String> nome;
	public static volatile SingularAttribute<Cidade, Long> codigoIbge;
	public static volatile SingularAttribute<Cidade, Byte> ddd;
	public static volatile ListAttribute<Cidade, Empresa> empresas;
}
