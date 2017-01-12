package br.com.cams7.casa_das_quentinhas.dao;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

/**
 * @author César Magalhães
 *
 */
public interface UsuarioDAO extends BaseDAO<Integer, Usuario> {

	/**
	 * @param id
	 *            ID do usuário
	 * @param email
	 *            E-mail do usuário
	 * @param senha
	 *            Senha do usuário
	 * @return Total de usuários atualizados
	 */
	int updateEmailAndSenha(Integer id, String email, String senha);

	/**
	 * @param email
	 *            E-mail do usuário
	 * @return Usuário
	 */
	Usuario getUsuarioByEmail(String email);

	/**
	 * @param email
	 *            E-mail do usuário
	 * @return ID do usuário
	 */
	Integer getUsuarioIdByEmail(String email);

	/**
	 * @param id
	 *            ID do usuário
	 * @return Senha criptografada
	 */
	String getUsuarioSenhaById(Integer id);

}
