package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO;
import br.com.cams7.casa_das_quentinhas.entity.Usuario;

/**
 * @author César Magalhães
 *
 */
public interface UsuarioService extends BaseService<Integer, Usuario>, UsuarioDAO {

	/**
	 * @param id
	 *            ID do usuário
	 * @param email
	 *            E-mail do usuário
	 * @return Verifica se o e-mail não foi cadastrado anteriormente
	 */
	boolean isEmailUnique(Integer id, String email);
}