/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.cams7.casa_das_quentinhas.model.Cliente.TipoContribuinte;


/**
 * @author cesar
 *
 */
@StaticMetamodel(Cliente.class)
public class Cliente_ {
	public static volatile SingularAttribute<Cliente, Integer> id;
	public static volatile SingularAttribute<Cliente, Cidade> cidade;
	public static volatile SingularAttribute<Cliente, Usuario> usuarioAcesso;
	public static volatile SingularAttribute<Cliente, Usuario> usuarioCadastro;
	public static volatile SingularAttribute<Cliente, String> nome;
	public static volatile SingularAttribute<Cliente, String> cpf;
	public static volatile SingularAttribute<Cliente, TipoContribuinte> tipoContribuinte;
	public static volatile SingularAttribute<Cliente, Date> nascimento;
	public static volatile SingularAttribute<Cliente, Contato> contato;
	public static volatile SingularAttribute<Cliente, Endereco> endereco;
	public static volatile SingularAttribute<Cliente, Manutencao> manutencao;
}
