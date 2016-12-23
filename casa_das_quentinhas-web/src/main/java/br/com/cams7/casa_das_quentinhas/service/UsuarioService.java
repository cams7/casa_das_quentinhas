package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.UsuarioDAO;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

public interface UsuarioService extends BaseService<Usuario, Integer>, UsuarioDAO {

	boolean isEmailUnique(Integer id, String email);
}