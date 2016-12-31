/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Manutencao.class)
public class Manutencao_ {
	public static volatile SingularAttribute<Manutencao, Date> cadastro;
	public static volatile SingularAttribute<Manutencao, Date> alteracao;
}
