/**
 * 
 */
package br.com.cams7.app.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cams7.app.AppException;
import br.com.cams7.app.SearchParams;
import br.com.cams7.app.SearchParams.SortOrder;
import br.com.cams7.app.entity.AbstractEntity;
import br.com.cams7.app.service.BaseService;
import br.com.cams7.app.utils.AppHelper;

/**
 * @author César Magalhães
 *
 * @param <PK>
 *            ID da entidade
 * @param <E>
 *            Entidade
 * @param <S>
 *            Service
 */
public abstract class AbstractBeanController<PK extends Serializable, E extends AbstractEntity<PK>, S extends BaseService<PK, E>>
		extends AbstractController<PK, E, S> implements BaseBeanController<PK, E> {

	// protected final String REGEX_URL =
	// "^http://([-a-z0-9:.]*|localhost:8080/[-a-z]*)";

	private final short MAX_RESULTS = 10;
	private final String PREVIOUS_PAGE = "previousPage";
	private final String REMOVE_PREVIOUS_PAGE = "removePreviousPage";
	private final String SUCESS_MESSAGE = "sucessMessage";

	@Autowired
	private MessageSource messageSource;

	public AbstractBeanController() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseBeanController#index(org.springframework.
	 * ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public final String index(ModelMap model, HttpServletRequest request) {
		cleanNavigationPreviousPages(request);
		setCommonAttributes(model);

		Integer offset = 0;
		String sortField = "id";
		SortOrder sortOrder = SortOrder.DESCENDING;

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sortOrder, getFilters());

		setIgnoredJoins();

		List<E> entities = getService().search(params);
		long count = getService().getTotalElements(getFilters());

		model.addAttribute(getListName(), entities);

		setPaginationAttribute(model, offset, sortField, sortOrder, null, count, MAX_RESULTS);

		return getIndexTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseBeanController#create(org.springframework
	 * .ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String create(ModelMap model, HttpServletRequest request) {
		setPreviousPage(model, request);
		setCommonAttributes(model);

		E entity = getNewEntity();

		model.addAttribute(getModelName(), entity);

		return getCreateTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseBeanController#store(br.com.cams7.app.
	 * entity.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String store(@Valid E entity, BindingResult result, ModelMap model, HttpServletRequest request) {
		setPreviousPageAtribute(model, request);
		setCommonAttributes(model);

		if (result.hasErrors())
			return getCreateTilesPage();

		getService().setUsername(getUsername());
		getService().persist(entity);

		sucessMessage(model);
		return redirectToPreviousPage(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseBeanController#show(java.io.Serializable,
	 * org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String show(@PathVariable PK id, ModelMap model, HttpServletRequest request) {
		setPreviousPage(model, request);
		setCommonAttributes(model);

		E entity = getEntity(id);

		model.addAttribute(getModelName(), entity);

		return getShowTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseBeanController#edit(java.io.Serializable,
	 * org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String edit(@PathVariable PK id, ModelMap model, HttpServletRequest request) {
		setPreviousPage(model, request);
		setCommonAttributes(model);
		setEditPage(model);

		E entity = getEntity(id);

		model.addAttribute(getModelName(), entity);

		return getEditTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseBeanController#update(br.com.cams7.app.
	 * entity.AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.io.Serializable,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String update(@Valid E entity, BindingResult result, ModelMap model, @PathVariable PK id,
			HttpServletRequest request) {
		setPreviousPageAtribute(model, request);
		setCommonAttributes(model);
		setEditPage(model);

		if (result.hasErrors())
			return getEditTilesPage();

		getService().update(entity);

		sucessMessage(model, entity);
		return redirectToPreviousPage(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.app.controller.BaseBeanController#destroy(java.io.
	 * Serializable, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ResponseEntity<Map<String, String>> destroy(@PathVariable PK id, HttpServletRequest request) {
		Response response;

		try {
			getService().delete(id);
			response = getDeleteResponse(request);
		} catch (Exception e) {
			response = getErrorResponse(e);
		}

		return new ResponseEntity<Map<String, String>>(getOnlyMessage(response), response.getStatus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseBeanController#list(org.springframework.
	 * ui.ModelMap, java.lang.Integer, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public final String list(ModelMap model, @RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {
		SortOrder sorting = SortOrder.get(sortOrder);

		Map<String, Object> filters = new HashMap<>();

		if (getFilters() != null)
			filters.putAll(getFilters());

		filters.put(SearchParams.GLOBAL_FILTER, query);
		final String[] globalFilters = getGlobalFilters();

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sorting, filters, globalFilters);

		setIgnoredJoins();

		List<E> entities = getService().search(params);
		long count = getService().getTotalElements(filters, globalFilters);

		model.addAttribute(getListName(), entities);

		setPaginationAttribute(model, offset, sortField, sorting, query, count, MAX_RESULTS);

		return getListTilesPage();
	}

	/**
	 * Define um atributo com a página principal carregada
	 * 
	 * @param model
	 * @param mainPage
	 */
	public static void setMainPage(ModelMap model, String mainPage) {
		model.addAttribute("mainPage", mainPage);
	}

	/**
	 * Define um atributo com o e-mail do usuário logado
	 * 
	 * @param model
	 */
	public static void setUsuarioLogado(ModelMap model) {
		model.addAttribute("loggedinuser", getUsername());
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	protected static String getUsername() {
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			username = ((UserDetails) principal).getUsername();
		else
			username = principal.toString();

		return username;
	}

	/**
	 * @return Mensagem
	 */
	protected final MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * Busca a entidade pelo ID
	 * 
	 * @param id
	 *            ID da entidade
	 * @return Entidade
	 */
	protected E getEntity(PK id) {
		E entity = getService().getById(id);
		return entity;
	}

	/**
	 * Cria uma nova entidade
	 * 
	 * @return Entidade
	 */
	protected E getNewEntity() {
		E entity = AppHelper.getNewEntity(ENTITY_TYPE);
		return entity;
	}

	/**
	 * @return Prefixo das páginas
	 */
	protected final String getMainPage() {
		return getModelName();
	}

	/**
	 * @return Página principal
	 */
	protected final String getIndexTilesPage() {
		return getModelName() + "_index";
	}

	/**
	 * @return Página de cadastro
	 */
	protected final String getCreateTilesPage() {
		return getModelName() + "_create";
	}

	/**
	 * @return Página de visualização
	 */
	protected final String getShowTilesPage() {
		return getModelName() + "_show";
	}

	/**
	 * @return Página de edição
	 */
	protected final String getEditTilesPage() {
		return getModelName() + "_edit";
	}

	/**
	 * @return Listagem
	 */
	protected final String getListTilesPage() {
		return getModelName() + "_list";
	}

	/**
	 * Define os atributos da paginação
	 * 
	 * @param model
	 * @param offset
	 * @param sortField
	 * @param sortOrder
	 * @param query
	 * @param count
	 * @param maxResults
	 */
	protected final void setPaginationAttribute(ModelMap model, Integer offset, String sortField, SortOrder sortOrder,
			String query, Long count, short maxResults) {
		model.addAttribute("offset", offset);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortOrder", sortOrder.getSorting());
		model.addAttribute("query", query);

		model.addAttribute("maxResults", maxResults);
		model.addAttribute("count", count);
		model.addAttribute("globalFilters",
				Arrays.asList(getGlobalFilters()).stream().collect(Collectors.joining(",")));
	}

	/**
	 * Atributos comuns
	 * 
	 * @param model
	 */
	protected final void setCommonAttributes(ModelMap model) {
		setUsuarioLogado(model);
		setMainPage(model);
	}

	/**
	 * Define um atributo na página de edição
	 * 
	 * @param model
	 */
	protected final void setEditPage(ModelMap model) {
		model.addAttribute("edit", true);
	}

	/**
	 * @param model
	 * @param request
	 */
	protected final void setPreviousPageAtribute(final ModelMap model, final HttpServletRequest request) {
		final Stack<String> NAVIGATION = getNavigationPreviousPage(request);
		if (NAVIGATION != null && !NAVIGATION.isEmpty())
			model.addAttribute(PREVIOUS_PAGE, NAVIGATION.peek());
		else
			model.addAttribute(PREVIOUS_PAGE, "");
	}

	/**
	 * Redireciona para a página anterior
	 * 
	 * @param request
	 * @return
	 */
	protected final String redirectToPreviousPage(final HttpServletRequest request) {
		final Stack<String> NAVIGATION = getNavigationPreviousPage(request);
		if (NAVIGATION == null || NAVIGATION.isEmpty())
			return "redirect:/" + getMainPage();

		return "redirect:" + NAVIGATION.peek();
	}

	/**
	 * Messagem de sucesso após a inclusão
	 * 
	 * @param model
	 */
	protected final void sucessMessage(ModelMap model) {
		sucessMessage(model, getStoreSucessMessage());
	}

	/**
	 * Mensagem de sucesso após a edição
	 * 
	 * @param model
	 * @param entity
	 */
	protected final void sucessMessage(ModelMap model, E entity) {
		sucessMessage(model, getUpdateSucessMessage(entity));
	}

	/**
	 * Na requisição via AJAX, retorna apenas a messagem
	 * 
	 * @param e
	 *            Exception
	 * @return
	 */
	protected final Map<String, String> getOnlyMessage(Response response) {
		@SuppressWarnings("unchecked")
		Map<String, String> body = (Map<String, String>) (Map<String, ?>) response.getBody();
		return body;
	}

	/**
	 * @param request
	 * @return
	 */
	protected final Response getDeleteResponse(final HttpServletRequest request) {
		Response response;

		final boolean REMOVE_PREVIOUS_PAGE = removePreviousPage(request);
		final Stack<String> NAVIGATION = getNavigationPreviousPage(request);

		if (REMOVE_PREVIOUS_PAGE && NAVIGATION != null && !NAVIGATION.isEmpty()) {
			String page = NAVIGATION.peek();
			page += "&" + SUCESS_MESSAGE + "=" + getDeleteSucessMessage();
			response = getDeleteResponse(page);
		} else
			response = getDeleteResponse();

		return response;
	}

	/**
	 * Na requisição via AJAX, retorna a mensagem de sucesso
	 * 
	 * @param entity
	 * @return
	 */
	protected final Response getSucessResponse(AbstractEntity<?> entity) {
		return getSucessResponse(entity, null, null);
	}

	/**
	 * Na requisição via AJAX, retorna as mensagens de erro
	 * 
	 * @param e
	 * @return
	 */
	protected final Response getErrorResponse(Exception e) {
		LOGGER.error(e.getMessage());

		Map<String, Serializable> response = getResponseBody(null, ResponseContent.MESSAGE, e.getMessage());
		HttpStatus status = INTERNAL_SERVER_ERROR;

		if (e instanceof AppException)
			status = ((AppException) e).getStatus();

		return new Response(response, status);
	}

	/**
	 * @return Página principal
	 */
	protected abstract String getModelName();

	/**
	 * @return Página de listagem
	 */
	protected abstract String getListName();

	/**
	 * @return Filtros globais
	 */
	protected abstract String[] getGlobalFilters();

	/**
	 * @return Messagem de sucesso na remoção dos dados
	 */
	protected abstract String getDeleteSucessMessage();

	/**
	 * @return Mensagem de sucesso na inclusão dos dados
	 */
	protected abstract String getStoreSucessMessage();

	/**
	 * @param entity
	 *            Entidade
	 * @return Mensagem de sucesso na atualização do dados
	 */
	protected abstract String getUpdateSucessMessage(E entity);

	/**
	 * Define a página principal
	 * 
	 * @param model
	 */
	private void setMainPage(ModelMap model) {
		setMainPage(model, getMainPage());
	}

	/**
	 * Define a página anterior
	 * 
	 * @param request
	 */
	private void setPreviousPage(final ModelMap model, final HttpServletRequest request) {
		final String PREVIOUS_PAGE = getPreviousPageWithoutParams(request);

		if (PREVIOUS_PAGE != null) {
			final Stack<String> NAVIGATION = createNavigationPreviousPages(request);

			if (removePreviousPage(request))
				NAVIGATION.pop();
			else
				NAVIGATION.push(PREVIOUS_PAGE + "?" + REMOVE_PREVIOUS_PAGE + "=true");

			setPreviousPageAtribute(model, request);
		} else
			cleanNavigationPreviousPages(request);

	}

	/**
	 * @param request
	 * @return
	 */
	private boolean removePreviousPage(final HttpServletRequest request) {
		final String PARAM = request.getParameter(REMOVE_PREVIOUS_PAGE);
		return PARAM != null && Boolean.valueOf(PARAM);
	}

	/**
	 * @param request
	 * @return
	 */
	private String getPreviousPageWithoutParams(final HttpServletRequest request) {
		final String PREVIOUS_PAGE = request.getHeader("Referer");

		if (PREVIOUS_PAGE == null)
			return null;

		final int INDEX = PREVIOUS_PAGE.indexOf("?");
		return PREVIOUS_PAGE.substring(0, INDEX > -1 ? INDEX : PREVIOUS_PAGE.length());
	}

	/**
	 * @param request
	 */
	private void cleanNavigationPreviousPages(final HttpServletRequest request) {
		request.getSession().setAttribute(PREVIOUS_PAGE, null);
	}

	/**
	 * @param request
	 */
	private Stack<String> createNavigationPreviousPages(final HttpServletRequest request) {
		if (getNavigationPreviousPage(request) == null)
			request.getSession().setAttribute(PREVIOUS_PAGE, new Stack<String>());

		return getNavigationPreviousPage(request);
	}

	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Stack<String> getNavigationPreviousPage(final HttpServletRequest request) {
		return (Stack<String>) request.getSession().getAttribute(PREVIOUS_PAGE);
	}

	// private String getRegexMainPage() {
	// return REGEX_URL + "/[a-z]*$";
	// }
	//
	// private String getRegexCreatePage() {
	// return REGEX_URL + "/[a-z]*/create$";
	// }
	//
	// private String getRegexViewPage() {
	// return REGEX_URL + "/[a-z]*/[0-9]*$";
	// }
	//
	// private String getRegexEditPage() {
	// return REGEX_URL + "/[a-z]*/[0-9]*/edit$";
	// }

	/**
	 * Define um atributo com a mensagem de sucesso
	 * 
	 * @param model
	 * @param message
	 */
	private void sucessMessage(ModelMap model, String message) {
		model.addAttribute(SUCESS_MESSAGE, message);
	}

	/**
	 * Na requisição via AJAX, retorna a mensagem de exclusão dos dados
	 * 
	 * @return
	 */
	private Response getDeleteResponse() {
		return getSucessResponse(null, ResponseContent.MESSAGE, getDeleteSucessMessage());
	}

	/**
	 * @param previousPage
	 * @return
	 */
	private Response getDeleteResponse(final String previousPage) {
		return getSucessResponse(null, ResponseContent.PREVIOUS_PAGE, previousPage);
	}

	/**
	 * Na requisição via AJAX, retorna a mensagem de sucesso
	 * 
	 * @param entity
	 * @param message
	 * @return
	 */
	private Response getSucessResponse(AbstractEntity<?> entity, ResponseContent content, String value) {
		Map<String, Serializable> response = getResponseBody(entity, content, value);
		return new Response(response, OK);
	}

	/**
	 * Retorna o corpo da mensagem
	 * 
	 * @param entity
	 * @param message
	 * @return
	 */
	private Map<String, Serializable> getResponseBody(AbstractEntity<?> entity, ResponseContent content, String value) {
		Map<String, Serializable> objectMessage = new HashMap<>();

		if (entity != null)
			objectMessage.put("entity", entity);
		if (content != null && value != null)
			objectMessage.put(content.key, value);

		return objectMessage;
	}

	/**
	 * @author César Magalhães
	 *
	 */
	protected final class Response {
		private Map<String, Serializable> body;
		private HttpStatus status;

		private Response(Map<String, Serializable> body, HttpStatus status) {
			super();

			this.body = body;
			this.status = status;
		}

		public final Map<String, Serializable> getBody() {
			return body;
		}

		public final HttpStatus getStatus() {
			return status;
		}
	}

	private enum ResponseContent {
		MESSAGE("message"), PREVIOUS_PAGE("previousPage");

		private String key;

		private ResponseContent(String key) {
			this.key = key;
		}

	}

}
