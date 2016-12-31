/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.cams7.casa_das_quentinhas.model.Produto.Tamanho;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Produto.class)
public class Produto_ {
	public static volatile SingularAttribute<Produto, Integer> id;
	public static volatile SingularAttribute<Produto, Usuario> usuarioCadastro;
	public static volatile SingularAttribute<Produto, String> nome;
	public static volatile SingularAttribute<Produto, String> ingredientes;
	public static volatile SingularAttribute<Produto, Float> custo;
	public static volatile SingularAttribute<Produto, Tamanho> tamanho;
	public static volatile SingularAttribute<Produto, String> codigoNcm;
	public static volatile SingularAttribute<Produto, String> codigoCest;
	public static volatile SingularAttribute<Produto, Manutencao> manutencao;
}
