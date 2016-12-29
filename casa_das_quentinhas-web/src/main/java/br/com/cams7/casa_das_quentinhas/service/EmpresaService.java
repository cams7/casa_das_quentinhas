/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import br.com.cams7.app.service.BaseService;
import br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO;
import br.com.cams7.casa_das_quentinhas.model.Empresa;

/**
 * @author César Magalhães
 *
 */
public interface EmpresaService extends BaseService<Empresa, Integer>, EmpresaDAO {
	/**
	 * @param id
	 *            ID da empresa
	 * @param cnpj
	 *            CNPJ da empresa
	 * @return Verifica se o CNPJ não foi cadastrado anteriormente
	 */
	boolean isCNPJUnique(Integer id, String cnpj);

	/**
	 * @param empresaId
	 *            ID da empresa
	 * @param usuarioId
	 *            ID do usuário
	 * @param email
	 *            E-mail do usuário ou da empresa
	 * @return Verifica se o e-mail não foi cadastrado anteriormente
	 */
	boolean isEmailUnique(Integer empresaId, Integer usuarioId, String email);
}
