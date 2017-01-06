package br.com.cams7.casa_das_quentinhas.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Empresa.Tipo;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Controller
@RequestMapping("/entregador")
public class EntregadorController extends AbstractController<FuncionarioService, Funcionario, Integer> {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.AbstractController#store(br.
	 * com.cams7.casa_das_quentinhas.model.AbstractEntity,
	 * org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap)
	 */
	@Override
	public String store(@Valid @ModelAttribute("entregador") Funcionario entregador, BindingResult result,
			ModelMap model, @RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		Usuario usuario = entregador.getUsuario();
		Empresa empresa = entregador.getEmpresa();

		if (empresa.getId() == null) {
			FieldError empresaError = new FieldError("entregador", "empresa.id",
					messageSource.getMessage("NotNull.entregador.empresa.id", null, Locale.getDefault()));
			result.addError(empresaError);
		}

		if (usuario.getSenha().isEmpty()) {
			FieldError senhaError = new FieldError("entregador", "usuario.senha",
					messageSource.getMessage("NotEmpty.usuario.senha", null, Locale.getDefault()));
			result.addError(senhaError);
		}

		setNotEmptyConfirmacaoError(usuario, result, true);
		setSenhaNotEqualsConfirmacaoError(usuario, result);

		setEmailNotUniqueError(usuario, result);
		setCPFNotUniqueError(entregador, result);

		setCommonAttributes(model);
		incrementLastLoadedPage(model, lastLoadedPage);

		if (result.hasErrors())
			return getCreateTilesPage();

		entregador.setCpf(entregador.getUnformattedCpf());
		entregador.setCelular(entregador.getUnformattedCelular());
		entregador.setFuncao(Funcao.ENTREGADOR);

		getService().setUsername(getUsername());
		getService().persist(entregador);

		return redirectMainPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.AbstractController#update(br.com.cams7.app.
	 * model.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.io.Serializable, java.lang.Integer)
	 */
	@Override
	public String update(@Valid @ModelAttribute("entregador") Funcionario entregador, BindingResult result,
			ModelMap model, @PathVariable Integer id,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {
		Usuario usuario = entregador.getUsuario();

		setNotEmptyConfirmacaoError(usuario, result, !usuario.getSenha().isEmpty());
		setSenhaNotEqualsConfirmacaoError(usuario, result);

		setEmailNotUniqueError(usuario, result);
		setCPFNotUniqueError(entregador, result);

		setCommonAttributes(model);
		setEditPage(model);
		incrementLastLoadedPage(model, lastLoadedPage);

		if (result.hasErrors())
			return getEditTilesPage();

		entregador.setCpf(entregador.getUnformattedCpf());
		entregador.setCelular(entregador.getUnformattedCelular());

		getService().update(entregador);

		return redirectMainPage();
	}

	@GetMapping(value = "/empresas/{razaoSocialOrCnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getEmpresas(@PathVariable String razaoSocialOrCnpj) {
		Map<Integer, String> empresas = empresaService.getEmpresasByRazaoSocialOrCnpj(razaoSocialOrCnpj, Tipo.ENTREGA);

		return new ResponseEntity<Map<Integer, String>>(empresas, HttpStatus.OK);
	}

	/**
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
			FieldError confirmacaoError = new FieldError("entregador", "usuario.confirmacaoSenha",
					messageSource.getMessage("NotEmpty.usuario.confirmacaoSenha", null, Locale.getDefault()));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * Verifica se senha informada é mesma do campo confirmação
	 * 
	 * @param usuario
	 *            Usuário
	 * @param result
	 */
	private void setSenhaNotEqualsConfirmacaoError(Usuario usuario, BindingResult result) {
		if (result.getFieldErrorCount("usuario.confirmacaoSenha") > 0)
			return;

		if (!usuario.getSenha().isEmpty() && !usuario.getConfirmacaoSenha().isEmpty()
				&& !usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			FieldError confirmacaoError = new FieldError("entregador", "usuario.confirmacaoSenha",
					messageSource.getMessage("NotEquals.usuario.confirmacaoSenha", null, Locale.getDefault()));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * Verifica se o e-mail informado não foi cadastrado anteriormente
	 * 
	 * @param usuario
	 * @param result
	 */
	private void setEmailNotUniqueError(Usuario usuario, BindingResult result) {
		if (result.getFieldErrorCount("usuario.email") > 0)
			return;

		if (!usuarioService.isEmailUnique(usuario.getId(), usuario.getEmail())) {
			FieldError emailError = new FieldError("entregador", "usuario.email", messageSource
					.getMessage("NonUnique.usuario.email", new String[] { usuario.getEmail() }, Locale.getDefault()));
			result.addError(emailError);
		}
	}

	/**
	 * Verifica se o CPF informado não foi cadastrado anteriormente
	 * 
	 * @param entregador
	 * @param result
	 */
	private void setCPFNotUniqueError(Funcionario entregador, BindingResult result) {
		if (result.getFieldErrorCount("cpf") > 0)
			return;

		String cpf = entregador.getUnformattedCpf();

		if (!getService().isCPFUnique(entregador.getId(), cpf)) {
			FieldError cpfError = new FieldError("entregador", "cpf", messageSource
					.getMessage("NonUnique.entregador.cpf", new String[] { entregador.getCpf() }, Locale.getDefault()));
			result.addError(cpfError);
		}
	}

	@Override
	protected String getEntityName() {
		return "entregador";
	}

	@Override
	protected String getListName() {
		return "entregadores";
	}

	@Override
	protected String getMainPage() {
		return "entregador";
	}

	@Override
	protected String getIndexTilesPage() {
		return "entregador_index";
	}

	@Override
	protected String getCreateTilesPage() {
		return "entregador_create";
	}

	@Override
	protected String getShowTilesPage() {
		return "entregador_show";
	}

	@Override
	protected String getEditTilesPage() {
		return "entregador_edit";
	}

	@Override
	protected String getListTilesPage() {
		return "entregador_list";
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "nome", "cpf", "celular", "usuario.email", "empresa.razaoSocial", "empresa.cnpj" };
	}

	@Override
	protected Funcionario getNewEntity() {
		Funcionario entregador = new Funcionario();
		entregador.setUsuario(new Usuario());
		entregador.setEmpresa(new Empresa());
		return entregador;
	}

	@Override
	protected Funcionario getEntity(Integer id) {
		return getService().getFuncionarioById(id);
	}

	@Override
	protected Map<String, Object> getFilters() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("funcao", Funcao.ENTREGADOR);
		return filters;
	}

}