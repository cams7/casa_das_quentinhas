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
@StaticMetamodel(Estado.class)
public class Estado_ {
	public static volatile SingularAttribute<Estado, Short> id;
	public static volatile SingularAttribute<Estado, String> nome;
	public static volatile SingularAttribute<Estado, String> sigla;
	public static volatile SingularAttribute<Estado, Byte> codigoIbge;
	public static volatile SetAttribute<Estado, Cidade> cidades;
}
