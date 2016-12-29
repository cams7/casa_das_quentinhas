/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Empresa;

/**
 * @author César Magalhães
 *
 */
public interface EmpresaDAO extends BaseDAO<Empresa, Integer> {

	Map<Integer, String> getEmpresasByRazaoSocialOrCnpj(String nomeOrCnpj);
}
