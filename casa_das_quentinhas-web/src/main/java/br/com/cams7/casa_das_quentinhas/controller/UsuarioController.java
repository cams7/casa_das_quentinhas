package br.com.cams7.casa_das_quentinhas.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.FuncionarioService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("funcionarios")
public class UsuarioController extends AbstractController<UsuarioService, Usuario, Integer> {

	@Autowired
	private FuncionarioService funcionarioService;

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
	public String store(@Valid Usuario usuario, BindingResult result, ModelMap model,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {
		if (usuario.getSenha().isEmpty()) {
			FieldError senhaError = new FieldError("usuario", "senha",
					messageSource.getMessage("NotEmpty.usuario.senha", null, Locale.getDefault()));
			result.addError(senhaError);
		}

		setNotEmptyConfirmacaoError(usuario, result, true);
		setSenhaNotEqualsConfirmacaoError(usuario, result);

		if (!getService().isEmailUnique(usuario.getId(), usuario.getEmail())) {
			FieldError emailError = new FieldError("usuario", "email", messageSource.getMessage("non.unique.email",
					new String[] { usuario.getEmail() }, Locale.getDefault()));
			result.addError(emailError);
		}

		return super.store(usuario, result, model, lastLoadedPage);
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
	public String update(@Valid Usuario usuario, BindingResult result, ModelMap model, @PathVariable Integer id,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		setNotEmptyConfirmacaoError(usuario, result, !usuario.getSenha().isEmpty());
		setSenhaNotEqualsConfirmacaoError(usuario, result);

		return super.update(usuario, result, model, id, lastLoadedPage);
	}

	private void setNotEmptyConfirmacaoError(Usuario usuario, BindingResult result, boolean senhaInformada) {
		if (senhaInformada && usuario.getConfirmacaoSenha().isEmpty()) {
			FieldError confirmacaoError = new FieldError("usuario", "confirmacaoSenha",
					messageSource.getMessage("NotEmpty.usuario.confirmacaoSenha", null, Locale.getDefault()));
			result.addError(confirmacaoError);
		}
	}

	private void setSenhaNotEqualsConfirmacaoError(Usuario usuario, BindingResult result) {
		if (!usuario.getSenha().isEmpty() && !usuario.getConfirmacaoSenha().isEmpty()
				&& !usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			FieldError confirmacaoError = new FieldError("usuario", "confirmacaoSenha",
					messageSource.getMessage("senha.notEquals.confirmacao", null, Locale.getDefault()));
			result.addError(confirmacaoError);
		}
	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("funcionarios")
	public List<Funcionario> initializeFuncionarios() {
		List<Funcionario> funcionarios = funcionarioService.getAll();
		return funcionarios;
	}

	@Override
	protected String getEntityName() {
		return "usuario";
	}

	@Override
	protected String getListName() {
		return "usuarios";
	}

	@Override
	protected String getMainPage() {
		return "usuario";
	}

	@Override
	protected String getIndexTilesPage() {
		return "usuario_index";
	}

	@Override
	protected String getCreateTilesPage() {
		return "usuario_create";
	}

	@Override
	protected String getShowTilesPage() {
		return "usuario_show";
	}

	@Override
	protected String getEditTilesPage() {
		return "usuario_edit";
	}

	@Override
	protected String getListTilesPage() {
		return "usuario_list";
	}

	@Override
	protected Usuario getEntity(Integer id) {
		Usuario usuario = getService().getUsuarioById(id);
		return usuario;
	}

	@Override
	protected String[] getGlobalFilters() {
		return new String[] { "email" };
	}

}