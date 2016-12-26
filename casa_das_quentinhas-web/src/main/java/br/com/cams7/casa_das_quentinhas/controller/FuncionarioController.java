package br.com.cams7.casa_das_quentinhas.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.casa_das_quentinhas.model.Funcionario;
import br.com.cams7.casa_das_quentinhas.model.Funcionario.Funcao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Controller
@RequestMapping("/funcionario")
@SessionAttributes("funcionarioFuncoes")
public class FuncionarioController extends AbstractController<FuncionarioService, Funcionario, Integer> {

	@Autowired
	private UsuarioService usuarioService;

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

		if (usuario.getSenha().isEmpty()) {
			FieldError senhaError = new FieldError("funcionario", "usuario.senha",
					messageSource.getMessage("NotEmpty.usuario.senha", null, Locale.getDefault()));
			result.addError(senhaError);
		}

		setNotEmptyConfirmacaoError(usuario, result, true);
		setSenhaNotEqualsConfirmacaoError(usuario, result);

		if (!usuarioService.isEmailUnique(usuario.getId(), usuario.getEmail())) {
			FieldError emailError = new FieldError("funcionario", "usuario.email", messageSource
					.getMessage("non.unique.email", new String[] { usuario.getEmail() }, Locale.getDefault()));
			result.addError(emailError);
		}

		setUsuarioLogado(model);
		incrementLastLoadedPage(model, lastLoadedPage);

		if (result.hasErrors())
			return getCreateTilesPage();

		usuarioService.persist(usuario);

		funcionario.setId(usuario.getId());
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

		setEditPage(model);
		setUsuarioLogado(model);
		incrementLastLoadedPage(model, lastLoadedPage);

		if (result.hasErrors())
			return getEditTilesPage();

		usuarioService.update(usuario);

		getService().update(funcionario);

		return redirectMainPage();
	}

	@Override
	public ResponseEntity<Void> destroy(@PathVariable Integer id) {

		getService().delete(id);
		usuarioService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private void setNotEmptyConfirmacaoError(Usuario usuario, BindingResult result, boolean senhaInformada) {
		if (senhaInformada && usuario.getConfirmacaoSenha().isEmpty()) {
			FieldError confirmacaoError = new FieldError("funcionario", "usuario.confirmacaoSenha",
					messageSource.getMessage("NotEmpty.usuario.confirmacaoSenha", null, Locale.getDefault()));
			result.addError(confirmacaoError);
		}
	}

	private void setSenhaNotEqualsConfirmacaoError(Usuario usuario, BindingResult result) {
		if (!usuario.getSenha().isEmpty() && !usuario.getConfirmacaoSenha().isEmpty()
				&& !usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			FieldError confirmacaoError = new FieldError("funcionario", "usuario.confirmacaoSenha",
					messageSource.getMessage("senha.notEquals.confirmacao", null, Locale.getDefault()));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * This method will provide UserProfile list to views
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
	protected Funcionario getNewEntity() {
		Funcionario funcionario = new Funcionario();
		funcionario.setUsuario(new Usuario());
		return funcionario;
	}

	@Override
	protected Funcionario getEntity(Integer id) {
		Funcionario funcionario = getService().getFuncionarioById(id);
		return funcionario;
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "usuario.email" };
	}

}