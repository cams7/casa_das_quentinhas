/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.model;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.cams7.casa_das_quentinhas.model.Empresa.RegimeTributario;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Empresa.class)
public class Empresa_ {
	public static volatile SingularAttribute<Empresa, Integer> id;
	public static volatile SingularAttribute<Empresa, Cidade> cidade;
	public static volatile SingularAttribute<Empresa, Usuario> usuarioAcesso;
	public static volatile SingularAttribute<Empresa, Usuario> usuarioCadastro;
	public static volatile SingularAttribute<Empresa, Tipo> tipo;
	public static volatile SingularAttribute<Empresa, String> razaoSocial;
	public static volatile SingularAttribute<Empresa, String> nomeFantasia;
	public static volatile SingularAttribute<Empresa, String> cnpj;
	public static volatile SingularAttribute<Empresa, String> inscricaoEstadual;
	public static volatile SingularAttribute<Empresa, String> inscricaoEstadualST;
	public static volatile SingularAttribute<Empresa, String> inscricaoMuncipal;
	public static volatile SingularAttribute<Empresa, String> codigoCnae;
	public static volatile SingularAttribute<Empresa, RegimeTributario> regimeTributario;
	public static volatile SingularAttribute<Empresa, Contato> contato;
	public static volatile SingularAttribute<Empresa, Endereco> endereco;
	public static volatile SingularAttribute<Empresa, Date> cadastro;
	public static volatile SingularAttribute<Empresa, Date> alteracao;
	public static volatile ListAttribute<Empresa, Funcionario> funcionarios;
}
