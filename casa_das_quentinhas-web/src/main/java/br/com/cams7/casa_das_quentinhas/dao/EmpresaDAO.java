/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.model.Usuario.Relacao;

/**
 * @author César Magalhães
 *
 */
public interface EmpresaDAO extends BaseDAO<Empresa, Integer> {

	/**
	 * @param id
	 *            ID da empresa
	 * @param tipos
	 *            Tipos de empresa
	 * @return Empresa
	 */
	Empresa getEmpresaByIdAndTipos(Integer id, Tipo... tipos);

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
	 * @param empresaId
	 *            ID da empresa
	 * @param relacao
	 *            Relação entre o cliente e o usuário
	 * @return ID do usuário
	 */
	Integer getUsuarioIdByEmpresaId(Integer empresaId, Relacao relacao);

	/**
	 * @param nomeOrCnpj
	 *            Nome ou CNPJ
	 * @param tipo
	 *            Tipo de empresa
	 * @return Empresas
	 */
	Map<Integer, String> getEmpresasByRazaoSocialOrCnpj(String nomeOrCnpj, Tipo tipo);
}
