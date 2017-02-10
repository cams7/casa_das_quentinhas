package br.com.cams7.casa_das_quentinhas.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cams7.app.SearchParams;
import br.com.cams7.app.SearchParams.SortOrder;
import br.com.cams7.app.controller.AbstractBeanController;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario;
import br.com.cams7.casa_das_quentinhas.entity.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.entity.Pedido;
import br.com.cams7.casa_das_quentinhas.entity.Usuario;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;
import br.com.cams7.casa_das_quentinhas.service.PedidoService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

/**
 * @author César Magalhães
 *
 */
public abstract class AbstractFuncionarioController
		extends AbstractBeanController<Integer, Funcionario, FuncionarioService> {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractBeanController#show(java.io.
	 * Serializable, org.springframework.ui.ModelMap)
	 */
	@Override
	public String show(@PathVariable Integer id, ModelMap model) {
		loadPedidos(id, model, 0, "id", SortOrder.DESCENDING);

		return super.show(id, model);
	}

	@GetMapping(value = "/{funcionarioId}/pedidos")
	@ResponseStatus(OK)
	public String pedidos(@PathVariable Integer funcionarioId, ModelMap model,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {

		loadPedidos(funcionarioId, model, offset, sortField, SortOrder.get(sortOrder));

		return "pedido_list";
	}

	protected String storeFuncionario(Funcionario funcionario, BindingResult result, ModelMap model,
			HttpServletRequest request) {
		setCommonAttributes(model);
		incrementPreviousPage(model, request);

		Usuario usuario = funcionario.getUsuario();

		// 1º validação
		if (usuario.getSenha().isEmpty()) {
			FieldError senhaError = new FieldError(getModelName(), "usuario.senha",
					messageSource.getMessage("NotEmpty.usuario.senha", null, LOCALE));
			result.addError(senhaError);
		}

		setNotEmptyConfirmacaoError(usuario, result, true);

		// 2º validação
		setSenhaNotEqualsConfirmacaoError(usuario, result);
		setEmailNotUniqueError(usuario, result);
		setCPFNotUniqueError(funcionario, result);

		if (result.hasErrors())
			return getCreateTilesPage();

		funcionario.setCpf(funcionario.getUnformattedCpf());
		funcionario.setCelular(funcionario.getUnformattedCelular());

		getService().setUsername(getUsername());
		getService().persist(funcionario, getPossiveisFuncoes());

		sucessMessage(model);
		return redirectToPreviousPage(request);
	}

	protected String updateFuncionario(Funcionario funcionario, BindingResult result, ModelMap model, Integer id,
			HttpServletRequest request) {
		setCommonAttributes(model);
		incrementPreviousPage(model, request);
		setEditPage(model);

		Usuario usuario = funcionario.getUsuario();
		usuario.setId(funcionario.getId());

		// 1º validação
		setNotEmptyConfirmacaoError(usuario, result, !usuario.getSenha().isEmpty());

		// 2º validação
		setSenhaNotEqualsConfirmacaoError(usuario, result);
		setEmailNotUniqueError(usuario, result);
		setCPFNotUniqueError(funcionario, result);

		if (result.hasErrors())
			return getCreateTilesPage();

		funcionario.setCpf(funcionario.getUnformattedCpf());
		funcionario.setCelular(funcionario.getUnformattedCelular());

		getService().update(funcionario, getPossiveisFuncoes());

		sucessMessage(model, funcionario);
		return redirectToPreviousPage(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.AbstractController#destroy(java.io.
	 * Serializable)
	 */
	@Override
	public ResponseEntity<Map<String, String>> destroy(@PathVariable Integer id) {
		Response response;

		try {
			getService().delete(id, getPossiveisFuncoes());
			response = getDeleteResponse();
		} catch (Exception e) {
			response = getErrorResponse(e);
		}

		return new ResponseEntity<Map<String, String>>(getOnlyMessage(response), response.getStatus());
	}

	@Override
	protected Funcionario getNewEntity() {
		Funcionario funcionario = new Funcionario();
		funcionario.setUsuario(new Usuario());
		return funcionario;
	}

	@Override
	protected Funcionario getEntity(Integer id) {
		Funcionario funcionario = getService().getFuncionarioByIdAndFuncoes(id, getPossiveisFuncoes());
		return funcionario;
	}

	@Override
	protected Map<String, Object> getFilters() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("funcao", getPossiveisFuncoes());
		return filters;
	}

	protected abstract Funcao[] getPossiveisFuncoes();

	protected abstract void setFilterPedidos(Map<String, Object> filters, Integer entregadorId);

	protected UsuarioService getUsuarioService() {
		return usuarioService;
	}

	protected EmpresaService getEmpresaService() {
		return empresaService;
	}

	protected MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * 1º validação
	 * 
	 * Verifica se o campo de confirmação de senha não esta vazio, caso o campo
	 * senha tenha sido preenchido anteriormente
	 * 
	 * @param usuario
	 *            Usuário
	 * @param result
	 * @param senhaInformada
	 *            A senha foi informada
	 */
	private void setNotEmptyConfirmacaoError(Usuario usuario, BindingResult result, boolean senhaInformada) {
		if (senhaInformada && usuario.getConfirmacaoSenha().isEmpty()) {
			FieldError confirmacaoError = new FieldError(getModelName(), "usuario.confirmacaoSenha",
					messageSource.getMessage("NotEmpty.usuario.confirmacaoSenha", null, LOCALE));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * 2º validação
	 * 
	 * Verifica se senha informada é mesma do campo confirmação
	 * 
	 * @param usuario
	 *            Usuário
	 * @param result
	 */
	private void setSenhaNotEqualsConfirmacaoError(Usuario usuario, BindingResult result) {
		final String FIELD_NAME = "usuario.confirmacaoSenha";
		if (result.getFieldErrorCount(FIELD_NAME) > 0)
			return;

		if (!usuario.getSenha().isEmpty() && !usuario.getConfirmacaoSenha().isEmpty()
				&& !usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			FieldError confirmacaoError = new FieldError(getModelName(), FIELD_NAME,
					messageSource.getMessage("NotEquals.usuario.confirmacaoSenha", null, LOCALE));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * 2º validação
	 * 
	 * Verifica se o e-mail informado não foi cadastrado anteriormente
	 * 
	 * @param usuario
	 * @param result
	 */
	private void setEmailNotUniqueError(Usuario usuario, BindingResult result) {
		final String FIELD_NAME = "usuario.email";
		if (result.getFieldErrorCount(FIELD_NAME) > 0)
			return;

		if (!usuarioService.isEmailUnique(usuario.getId(), usuario.getEmail())) {
			FieldError emailError = new FieldError(getModelName(), FIELD_NAME,
					messageSource.getMessage("NonUnique.usuario.email", new String[] { usuario.getEmail() }, LOCALE));
			result.addError(emailError);
		}
	}

	/**
	 * 2º validação
	 * 
	 * Verifica se o CPF informado não foi cadastrado anteriormente
	 * 
	 * @param funcionario
	 * @param result
	 */
	private void setCPFNotUniqueError(Funcionario funcionario, BindingResult result) {
		final String FIELD_NAME = "cpf";
		if (result.getFieldErrorCount(FIELD_NAME) > 0)
			return;

		String cpf = funcionario.getUnformattedCpf();

		if (!getService().isCPFUnique(funcionario.getId(), cpf)) {
			FieldError cpfError = new FieldError(getModelName(), FIELD_NAME, messageSource
					.getMessage("NonUnique." + getModelName() + ".cpf", new String[] { funcionario.getCpf() }, LOCALE));
			result.addError(cpfError);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadPedidos(Integer funcionarioId, ModelMap model, Integer offset, String sortField,
			SortOrder sortOrder) {
		final short MAX_RESULTS = 5;

		Map<String, Object> filters = new HashMap<>();
		setFilterPedidos(filters, funcionarioId);

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sortOrder, filters);

		pedidoService.setIgnoredJoins();
		List<Pedido> pedidos = pedidoService.search(params);
		long count = pedidoService.getTotalElements(filters);

		model.addAttribute("pedidos", pedidos);

		setPaginationAttribute(model, offset, sortField, sortOrder, null, count, MAX_RESULTS);
	}

}