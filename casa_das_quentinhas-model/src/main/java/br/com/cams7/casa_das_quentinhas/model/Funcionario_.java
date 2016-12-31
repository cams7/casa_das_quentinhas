/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Funcionario.class)
public class Funcionario_ {
	public static volatile SingularAttribute<Funcionario, Integer> id;
	public static volatile SingularAttribute<Funcionario, Usuario> usuario;
	public static volatile SingularAttribute<Funcionario, Funcao> funcao;
	public static volatile SingularAttribute<Funcionario, Usuario> usuarioCadastro;
	public static volatile SingularAttribute<Funcionario, Empresa> empresa;
	public static volatile SingularAttribute<Funcionario, String> nome;
	public static volatile SingularAttribute<Funcionario, String> cpf;
	public static volatile SingularAttribute<Funcionario, String> rg;
	public static volatile SingularAttribute<Funcionario, String> celular;
	public static volatile SingularAttribute<Funcionario, Manutencao> manutencao;
}
