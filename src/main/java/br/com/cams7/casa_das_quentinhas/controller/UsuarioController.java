package br.com.cams7.casa_das_quentinhas.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cams7.casa_das_quentinhas.model.Autorizacao;
import br.com.cams7.casa_das_quentinhas.model.Usuario;
import br.com.cams7.casa_das_quentinhas.service.AutorizacaoService;
import br.com.cams7.casa_das_quentinhas.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("autorizacoes")
public class UsuarioController implements BaseController<Usuario, Integer> {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	AutorizacaoService autorizacaoService;

	@Autowired
	MessageSource messageSource;

	/**
	 * This method will list all existing usuarios.
	 */
	@Override
	public String index(ModelMap model) {
		// List<Usuario> usuarios = usuarioService.findAllUsuarios();

		Integer offset = 0;
		Integer maxResults = 10;

		List<Usuario> usuarios = usuarioService.listUsuarios(offset, maxResults);
		Long count = usuarioService.countUsuarios();

		model.addAttribute("usuarios", usuarios);
		setPaginationAttribute(model, offset, count);

		model.addAttribute("loggedinuser", getPrincipal());

		return "usuario_index";
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@Override
	public String create(ModelMap model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		// model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "usuario_create";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@Override
	public String store(@Valid Usuario usuario, BindingResult result, ModelMap model) {
		if (result.hasErrors())
			return "usuario_create";

		/*
		 * Preferred way to achieve uniqueness of field [sso] should be
		 * implementing custom @Unique annotation and applying it on field [sso]
		 * of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you
		 * can fill custom errors outside the validation framework as well while
		 * still using internationalized messages.
		 * 
		 */
		if (!usuarioService.isEmailUnique(usuario.getId(), usuario.getEmail())) {
			FieldError ssoError = new FieldError("usuario", "email", messageSource.getMessage("non.unique.email",
					new String[] { usuario.getEmail() }, Locale.getDefault()));
			result.addError(ssoError);
			return "usuario_create";
		}

		usuarioService.saveUsuario(usuario);

		model.addAttribute("success",
				"User " + usuario.getNome() + " " + usuario.getSobrenome() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		// return "success";
		return "redirect:/usuario/list";
	}

	@Override
	public String show(@PathVariable Integer id, ModelMap model) {
		Usuario usuario = usuarioService.findUsuarioById(id);
		model.addAttribute("usuario", usuario);
		model.addAttribute("loggedinuser", getPrincipal());
		return "usuario_show";
	}

	/**
	 * This method will provide the medium to update an existing user.
	 */
	@Override
	public String edit(@PathVariable Integer id, ModelMap model) {
		Usuario usuario = usuarioService.findUsuarioById(id);
		model.addAttribute("usuario", usuario);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "usuario_edit";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@Override
	public String update(@Valid Usuario usuario, BindingResult result, ModelMap model, @PathVariable Integer id) {
		model.addAttribute("edit", true);

		if (result.hasErrors())
			return "usuario_edit";

		/*
		 * //Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in
		 * UI which is a unique key to a User.
		 * if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
		 * FieldError ssoError =new
		 * FieldError("user","email",messageSource.getMessage(
		 * "non.unique.email", new String[]{user.getSsoId()},
		 * Locale.getDefault())); result.addError(ssoError); return
		 * "registration"; }
		 */

		usuarioService.updateUsuario(usuario);

		model.addAttribute("success",
				"User " + usuario.getNome() + " " + usuario.getSobrenome() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "redirect:/usuario/list";
	}

	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@Override
	public String destroy(@PathVariable Integer id) {
		usuarioService.deleteUsuarioById(id);
		return "redirect:/usuario/list";
	}

	@RequestMapping(value = "/pagination")
	public String list(ModelMap model, Integer offset, Integer maxResults) {
		List<Usuario> usuarios = usuarioService.listUsuarios(offset, maxResults);
		Long count = usuarioService.countUsuarios();

		model.addAttribute("usuarios", usuarios);
		setPaginationAttribute(model, offset, count);

		model.addAttribute("loggedinuser", getPrincipal());

		return "usuario_index";
	}

	private void setPaginationAttribute(ModelMap model, Integer offset, Long count) {
		model.addAttribute("count", count);
		model.addAttribute("offset", offset);
	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("autorizacoes")
	public List<Autorizacao> initializeAutorizacoes() {
		return autorizacaoService.findAllAutorizacoes();
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}