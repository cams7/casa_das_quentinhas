/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import static br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao.ENTREGADOR;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/" + EntregadorController.MODEL_NAME)
public class EntregadorController extends AbstractFuncionarioController {

	public static final String MODEL_NAME = "entregador";
	public static final String LIST_NAME = "entregadores";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.AbstractFuncionarioController
	 * #store(br.com.cams7.casa_das_quentinhas.model.Funcionario,
	 * org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.lang.Integer)
	 */
	@Override
	public String store(@Valid @ModelAttribute(MODEL_NAME) Funcionario entregador, BindingResult result, ModelMap model,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {
		return storeFuncionario(entregador, result, model, lastLoadedPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.AbstractFuncionarioController
	 * #update(br.com.cams7.casa_das_quentinhas.model.Funcionario,
	 * org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String update(@Valid @ModelAttribute(MODEL_NAME) Funcionario entregador, BindingResult result,
			ModelMap model, @PathVariable Integer id,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {
		return updateFuncionario(entregador, result, model, id, lastLoadedPage);
	}

	@GetMapping(value = "/empresas/{razaoSocialOrCnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getEmpresas(@PathVariable String razaoSocialOrCnpj) {
		Map<Integer, String> empresas = getEmpresaService().getEmpresasByRazaoSocialOrCnpj(razaoSocialOrCnpj,
				Tipo.ENTREGA);

		return new ResponseEntity<Map<Integer, String>>(empresas, HttpStatus.OK);
	}

	@Override
	protected String getModelName() {
		return MODEL_NAME;
	}

	@Override
	protected String getListName() {
		return LIST_NAME;
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "nome", "cpf", "celular", "usuario.email", "empresa.razaoSocial", "empresa.cnpj" };
	}

	@Override
	protected Funcionario getNewEntity() {
		Funcionario entregador = super.getNewEntity();
		entregador.setFuncao(ENTREGADOR);
		entregador.setEmpresa(new Empresa());
		return entregador;
	}

	@Override
	protected Funcionario getEntity(Integer id) {
		return getService().getFuncionarioByIdAndFuncoes(id, ENTREGADOR);
	}

	@Override
	protected Map<String, Object> getFilters() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("funcao", ENTREGADOR);
		return filters;
	}

}
