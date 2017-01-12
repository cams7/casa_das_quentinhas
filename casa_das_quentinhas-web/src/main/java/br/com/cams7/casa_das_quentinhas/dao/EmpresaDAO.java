/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.dao;

import java.util.Map;

import br.com.cams7.app.dao.BaseDAO;
import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Empresa.RelacionamentoEmpresa;
import br.com.cams7.casa_das_quentinhas.entity.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.entity.Usuario.RelacionamentoUsuario;

/**
 * @author César Magalhães
 *
 */
public interface EmpresaDAO extends BaseDAO<Integer, Empresa> {

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
	 * @param relacionamento
	 *            Relação entre a empresa e o usuário
	 * @return ID do usuário
	 */
	Integer getUsuarioIdByEmpresaId(Integer empresaId, RelacionamentoUsuario relacionamento);

	/**
	 * @param empresaId
	 *            ID da empresa
	 * @param relacionamento
	 *            Relação entre a empresa e o usuário
	 * @return Array contendo o id do usuário e data de cadastro da empresa
	 */
	Object[] getUsuarioIdAndEmpresaCadastroByEmpresaId(Integer empresaId, RelacionamentoUsuario relacionamento);

	/**
	 * Retorna o número total
	 * 
	 * @param empresaId
	 *            ID da empresa
	 * @param relacionamento
	 *            Relacionamento da empresa
	 * @return
	 */
	long countByEmpresaId(Integer empresaId, RelacionamentoEmpresa relacionamento);

	/**
	 * @param nomeOrCnpj
	 *            Nome ou CNPJ
	 * @param tipo
	 *            Tipo de empresa
	 * @return Empresas
	 */
	Map<Integer, String> getEmpresasByRazaoSocialOrCnpj(String nomeOrCnpj, Tipo tipo);
}
