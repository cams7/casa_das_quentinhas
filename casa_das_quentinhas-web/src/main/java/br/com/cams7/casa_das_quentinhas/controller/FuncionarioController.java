package br.com.cams7.casa_das_quentinhas.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.casa_das_quentinhas.model.Empresa;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Controller
@RequestMapping("/funcionario")
@SessionAttributes("funcionarioFuncoes")
public class FuncionarioController extends AbstractController<FuncionarioService, Funcionario, Integer> {

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
	public String store(@Valid Funcionario funcionario, BindingResult result, ModelMap model,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		Usuario usuario = funcionario.getUsuario();
		Empresa empresa = funcionario.getEmpresa();

		if (empresa.getId() == null) {
			FieldError empresaError = new FieldError("funcionario", "empresa.id",
					messageSource.getMessage("NotNull.funcionario.empresa.id", null, Locale.getDefault()));
			result.addError(empresaError);
		}

		if (usuario.getSenha().isEmpty()) {
			FieldError senhaError = new FieldError("funcionario", "usuario.senha",
					messageSource.getMessage("NotEmpty.usuario.senha", null, Locale.getDefault()));
			result.addError(senhaError);
		}

		setNotEmptyConfirmacaoError(usuario, result, true);
		setSenhaNotEqualsConfirmacaoError(usuario, result);

		setEmailNotUniqueError(usuario, result);
		setCPFNotUniqueError(funcionario, result);

		setUsuarioLogado(model);
		incrementLastLoadedPage(model, lastLoadedPage);
		setMainPage(model);

		if (result.hasErrors())
			return getCreateTilesPage();

		usuario = new Usuario(usuarioService.getUsuarioIdByEmail(getUsername()));
		funcionario.setUsuarioCadastro(usuario);

		funcionario.setCpf(funcionario.getUnformattedCpf());
		funcionario.setCelular(funcionario.getUnformattedCelular());

		getService().persist(funcionario);

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
	public String update(@Valid Funcionario funcionario, BindingResult result, ModelMap model, @PathVariable Integer id,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {
		Usuario usuario = funcionario.getUsuario();

		setNotEmptyConfirmacaoError(usuario, result, !usuario.getSenha().isEmpty());
		setSenhaNotEqualsConfirmacaoError(usuario, result);
		
		setEmailNotUniqueError(usuario, result);
		setCPFNotUniqueError(funcionario, result);

		setEditPage(model);
		setUsuarioLogado(model);
		incrementLastLoadedPage(model, lastLoadedPage);
		setMainPage(model);

		if (result.hasErrors())
			return getEditTilesPage();

		funcionario.setCpf(funcionario.getUnformattedCpf());
		funcionario.setCelular(funcionario.getUnformattedCelular());

		getService().update(funcionario);

		return redirectMainPage();
	}

	@GetMapping(value = { "/empresas/{razaoSocialOrCnpj}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Integer, String>> getEmpresas(@PathVariable String razaoSocialOrCnpj) {
		Map<Integer, String> empresas = empresaService.getEmpresasByRazaoSocialOrCnpj(razaoSocialOrCnpj);

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
			FieldError confirmacaoError = new FieldError("funcionario", "usuario.confirmacaoSenha",
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
			FieldError confirmacaoError = new FieldError("funcionario", "usuario.confirmacaoSenha",
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
			FieldError emailError = new FieldError("funcionario", "usuario.email", messageSource
					.getMessage("NonUnique.usuario.email", new String[] { usuario.getEmail() }, Locale.getDefault()));
			result.addError(emailError);
		}
	}

	/**
	 * Verifica se o CPF informado não foi cadastrado anteriormente
	 * 
	 * @param funcionario
	 * @param result
	 */
	private void setCPFNotUniqueError(Funcionario funcionario, BindingResult result) {
		if (result.getFieldErrorCount("cpf") > 0)
			return;

		String cpf = funcionario.getUnformattedCpf();

		if (!getService().isCPFUnique(funcionario.getId(), cpf)) {
			FieldError cpfError = new FieldError("funcionario", "cpf", messageSource.getMessage(
					"NonUnique.funcionario.cpf", new String[] { funcionario.getCpf() }, Locale.getDefault()));
			result.addError(cpfError);
		}
	}

	/**
	 * Possiveis funções do funcionário
	 */
	@ModelAttribute("funcionarioFuncoes")
	public Funcao[] initializeFuncoes() {
		Funcao[] funcoes = Funcao.values();
		return funcoes;
	}

	@Override
	protected String getEntityName() {
		return "funcionario";
	}

	@Override
	protected String getListName() {
		return "funcionarios";
	}

	@Override
	protected String getMainPage() {
		return "funcionario";
	}

	@Override
	protected String getIndexTilesPage() {
		return "funcionario_index";
	}

	@Override
	protected String getCreateTilesPage() {
		return "funcionario_create";
	}

	@Override
	protected String getShowTilesPage() {
		return "funcionario_show";
	}

	@Override
	protected String getEditTilesPage() {
		return "funcionario_edit";
	}

	@Override
	protected String getListTilesPage() {
		return "funcionario_list";
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "nome", "cpf", "usuario.email", "empresa.razaoSocial", "empresa.cnpj" };
	}

	@Override
	protected Funcionario getNewEntity() {
		Funcionario funcionario = new Funcionario();
		funcionario.setUsuario(new Usuario());
		funcionario.setEmpresa(new Empresa());
		return funcionario;
	}

	@Override
	protected Funcionario getEntity(Integer id) {
		Funcionario funcionario = getService().getFuncionarioById(id);
		return funcionario;
	}

}