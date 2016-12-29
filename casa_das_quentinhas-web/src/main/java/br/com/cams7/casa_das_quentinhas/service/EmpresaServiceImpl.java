/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cams7.app.service.AbstractService;
import br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO;
import br.com.cams7.casa_das_quentinhas.model.Empresa;

/**
 * @author César Magalhães
 *
 */
@Service
@Transactional
public class EmpresaServiceImpl extends AbstractService<EmpresaDAO, Empresa, Integer> implements EmpresaService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaById(java.lang.
	 * Integer)
	 */
	@Override
	public Empresa getEmpresaById(Integer id) {
		Empresa empresa = getDao().getEmpresaById(id);
		return empresa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresaByCnpj(java.
	 * lang.String)
	 */
	@Override
	public Integer getEmpresaIdByCnpj(String cnpj) {
		Integer empresaId = getDao().getEmpresaIdByCnpj(cnpj);
		return empresaId;
	}

	@Override
	public boolean isCNPJUnique(Integer id, String cnpj) {
		if (cnpj == null || cnpj.isEmpty())
			return true;

		Integer empresaId = getEmpresaIdByCnpj(cnpj);

		if (empresaId == null)
			return true;

		return id != null && empresaId == id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#
	 * getEmpresasByRazaoSocialOrCnpj(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getEmpresasByRazaoSocialOrCnpj(String razaoSocialOrCnpj) {
		Map<Integer, String> empresas = getDao().getEmpresasByRazaoSocialOrCnpj(razaoSocialOrCnpj);
		return empresas;
	}

}
