/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.cams7.casa_das_quentinhas.entity.Usuario.Tipo;

/**
 * @author cesar
 *
 */
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Integer> id;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> senhaCriptografada;
	public static volatile SingularAttribute<Usuario, Tipo> tipo;
	// public static volatile SingularAttribute<Usuario, Funcionario>
	// funcionario;
	public static volatile ListAttribute<Usuario, Funcionario> funcionarios;
	// public static volatile SingularAttribute<Usuario, Empresa> empresa;
	public static volatile ListAttribute<Usuario, Empresa> empresas;
	// public static volatile SingularAttribute<Usuario, Cliente> cliente;
	public static volatile ListAttribute<Usuario, Cliente> clientes;
	public static volatile ListAttribute<Usuario, Produto> produtos;
	public static volatile ListAttribute<Usuario, Pedido> pedidos;
}
