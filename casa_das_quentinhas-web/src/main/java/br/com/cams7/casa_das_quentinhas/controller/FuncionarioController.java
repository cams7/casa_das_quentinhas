/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.ATENDENTE;
import static br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao.GERENTE;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.casa_das_quentinhas.entity.Empresa;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/" + FuncionarioController.MODEL_NAME)
@SessionAttributes("funcionarioFuncoes")
public class FuncionarioController extends AbstractFuncionarioController {

	public static final String MODEL_NAME = "funcionario";
	public static final String LIST_NAME = "funcionarios";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#store(br.com.cams7.app
	 * .entity.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String store(@Valid Funcionario funcionario, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		return storeFuncionario(funcionario, result, model, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#update(br.com.cams7.
	 * app.entity.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.io.Serializable,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String update(@Valid Funcionario funcionario, BindingResult result, ModelMap model, @PathVariable Integer id,
			HttpServletRequest request) {
		return updateFuncionario(funcionario, result, model, id, request);
	}

	/**
	 * @return Possiveis funções do funcionário
	 */
	@ModelAttribute("funcionarioFuncoes")
	public Funcao[] initializeFuncoes() {
		return getPossiveisFuncoes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#getModelName()
	 */
	@Override
	protected String getModelName() {
		return MODEL_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#getListName()
	 */
	@Override
	protected String getListName() {
		return LIST_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractController#getIgnoredJoins()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Class<?>[] getIgnoredJoins() {
		return new Class<?>[] { Empresa.class };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getGlobalFilters()
	 */
	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "nome", "cpf", "celular", "usuario.email" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.AbstractFuncionarioController
	 * #getNewEntity()
	 */
	@Override
	protected Funcionario getNewEntity() {
		Funcionario funcionario = super.getNewEntity();
		funcionario.setEmpresa(new Empresa(1));
		return funcionario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.AbstractFuncionarioController
	 * #getEntity(java.lang.Integer)
	 */
	@Override
	protected Funcionario getEntity(Integer id) {
		Funcionario funcionario = super.getEntity(id);
		funcionario.setEmpresa(new Empresa(1));
		return funcionario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.AbstractFuncionarioController
	 * #getPossiveisFuncoes()
	 */
	@Override
	protected Funcao[] getPossiveisFuncoes() {
		return new Funcao[] { GERENTE, ATENDENTE };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.AbstractFuncionarioController
	 * #setFilterPedidos(java.util.Map, java.lang.Integer)
	 */
	@Override
	protected void setFilterPedidos(Map<String, Object> filters, Integer funcionarioId) {
		filters.put("usuarioCadastro.id", funcionarioId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getDeleteSucessMessage
	 * ()
	 */
	@Override
	protected String getDeleteSucessMessage() {
		return getMessageSource().getMessage("funcionario.successfully.removed", null, LOCALE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getStoreSucessMessage(
	 * )
	 */
	@Override
	protected String getStoreSucessMessage() {
		return getMessageSource().getMessage("funcionario.successfully.registered", null, LOCALE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractBeanController#getUpdateSucessMessage
	 * (br.com.cams7.app.entity.AbstractEntity)
	 */
	@Override
	protected String getUpdateSucessMessage(Funcionario funcionario) {
		return getMessageSource().getMessage("funcionario.successfully.updated",
				new String[] { funcionario.getNomeWithCpf() }, LOCALE);
	}

}
