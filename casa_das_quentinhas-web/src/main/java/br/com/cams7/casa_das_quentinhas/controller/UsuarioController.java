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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.app.controller.AbstractController;
import br.com.cams7.casa_das_quentinhas.model.Autorizacao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.AutorizacaoService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("autorizacoes")
public class UsuarioController extends AbstractController<UsuarioService, Usuario, Integer> {

	@Autowired
	private AutorizacaoService autorizacaoService;

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
			FieldError emailError = new FieldError("usuario", "senha",
					messageSource.getMessage("NotEmpty.usuario.password", null, Locale.getDefault()));
			result.addError(emailError);
		}

		if (!getService().isEmailUnique(usuario.getId(), usuario.getEmail())) {
			FieldError emailError = new FieldError("usuario", "email", messageSource.getMessage("non.unique.email",
					new String[] { usuario.getEmail() }, Locale.getDefault()));
			result.addError(emailError);
		}

		return super.store(usuario, result, model, lastLoadedPage);
	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("autorizacoes")
	public List<Autorizacao> initializeAutorizacoes() {
		List<Autorizacao> autorizacoes = autorizacaoService.getAll();
		return autorizacoes;
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
		return new String[] { "nome", "sobrenome", "email" };
	}

}