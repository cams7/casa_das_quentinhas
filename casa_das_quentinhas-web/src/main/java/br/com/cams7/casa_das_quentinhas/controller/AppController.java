package br.com.cams7.casa_das_quentinhas.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cams7.app.controller.AbstractBeanController;
import br.com.cams7.casa_das_quentinhas.service.EmpresaService;

/**
 * @author César Magalhães
 *
 */
@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	private AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	private EmpresaService empresaService;

	/**
	 * Página home
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping
	@ResponseStatus(OK)
	public String home(ModelMap model) {
		AbstractBeanController.setUsuarioLogado(model);
		AbstractBeanController.setMainPage(model, "home");

		return "home";
	}

	/**
	 * Página de informação sobre a empresa
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "info")
	@ResponseStatus(OK)
	public String info(ModelMap model) {
		AbstractBeanController.setUsuarioLogado(model);
		model.addAttribute("empresa", empresaService.getEmpresaByIdAndTipos(1));

		return "info";
	}

	/**
	 * This method handles login GET requests. If users is already logged-in and
	 * tries to goto login page again, will be redirected to list page.
	 * 
	 * @return
	 */
	@GetMapping(value = "login")
	// @ResponseStatus(OK)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous())
			return "login";

		return "redirect:/";
	}

	/**
	 * This method handles logout requests. Toggle the handlers if you are
	 * RememberMe functionality is useless in your app.
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "logout")
	// @ResponseStatus(OK)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			// new SecurityContextLogoutHandler().logout(request, response,
			// auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	/**
	 * This method handles Access-Denied redirect.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/unauthorized")
	@ResponseStatus(UNAUTHORIZED)
	public String accessDeniedPage(ModelMap model) {
		return "unauthorized";
	}

	/**
	 * This method returns true if users is already authenticated [logged-in],
	 * else false.
	 * 
	 * @return
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

}
