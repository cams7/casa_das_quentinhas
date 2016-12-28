/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.service;

import java.util.Set;

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
	 * br.com.cams7.casa_das_quentinhas.dao.EmpresaDAO#getEmpresasByNome(java.
	 * lang.String)
	 */
	@Override
	public Set<Empresa> getEmpresasByNomeOrCnpj(String nomeOrCnpj) {
		Set<Empresa> empresas = getDao().getEmpresasByNomeOrCnpj(nomeOrCnpj);
		return empresas;
	}

}
