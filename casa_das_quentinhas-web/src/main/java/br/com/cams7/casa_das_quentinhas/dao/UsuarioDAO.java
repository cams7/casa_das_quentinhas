package br.com.cams7.casa_das_quentinhas.dao;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Usuario;

public interface UsuarioDAO extends BaseDAO<Usuario, Integer> {

//	Usuario getUsuarioById(Integer id);

	Usuario getUsuarioByEmail(String email);

	String getUsuarioSenhaById(Integer id);
}
