/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;

/**
 * @author César Magalhães
 *
 */
public interface EmpresaDAO extends BaseDAO<Empresa, Integer> {

	/**
	 * @param id
	 *            ID da empresa
	 * @return Empresa
	 */
	Empresa getEmpresaById(Integer id);

	/**
	 * @param cnpj
	 *            CNPJ da empresa
	 * @return ID da empresa
	 */
	Integer getEmpresaIdByCnpj(String cnpj);

	/**
	 * @param email
	 *            E-mail da empresa
	 * @return ID da empresa
	 */
	Integer getEmpresaIdByEmail(String email);

	/**
	 * @param empresaId
	 *            ID da empresa
	 * @return ID do usuário
	 */
	Integer getUsuarioAcessoIdByEmpresaId(Integer empresaId);

	/**
	 * @param id
	 *            ID da empresa
	 * @return Tipo de empresa
	 */
	Tipo getEmpresaIipoById(Integer id);

	/**
	 * @param nomeOrCnpj
	 *            Nome ou CNPJ
	 * @param tipo
	 *            Tipo de empresa
	 * @return Empresas
	 */
	Map<Integer, String> getEmpresasByRazaoSocialOrCnpj(String nomeOrCnpj, Tipo tipo);
}
