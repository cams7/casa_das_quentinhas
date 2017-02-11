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

	@Autowired
	private MessageSource messageSource;

	protected final String PREVIOUS_PAGE = "previousPage";
	private final short MAX_RESULTS = 10;

	public AbstractBeanController() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseController#index(org.springframework.ui.
	 * ModelMap)
	 */

	@Override
	public final String index(ModelMap model) {
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
	 * br.com.cams7.app.controller.BaseController#create(org.springframework.ui.
	 * ModelMap)
	 */
	@Override
	public String create(ModelMap model, HttpServletRequest request) {
		setPreviousPage(request);
		setCommonAttributes(model);
		setPreviousPage(model, 1);

		E entity = getNewEntity();

		model.addAttribute(getModelName(), entity);

		return getCreateTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseController#store(br.com.cams7.app.model.
	 * AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.lang.Integer)
	 */
	@Override
	public String store(@Valid E entity, BindingResult result, ModelMap model, HttpServletRequest request) {
		setCommonAttributes(model);

		incrementPreviousPage(model, request);

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
	 * br.com.cams7.app.controller.BaseController#show(java.io.Serializable,
	 * org.springframework.ui.ModelMap)
	 */
	@Override
	public String show(@PathVariable PK id, ModelMap model) {
		setCommonAttributes(model);

		E entity = getEntity(id);

		model.addAttribute(getModelName(), entity);

		return getShowTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseController#edit(java.io.Serializable,
	 * org.springframework.ui.ModelMap)
	 */
	@Override
	public String edit(@PathVariable PK id, ModelMap model, HttpServletRequest request) {
		setPreviousPage(request);
		setCommonAttributes(model);
		setPreviousPage(model, 1);
		setEditPage(model);

		E entity = getEntity(id);

		model.addAttribute(getModelName(), entity);

		return getEditTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseController#update(br.com.cams7.app.model.
	 * AbstractEntity, org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.io.Serializable, java.lang.Integer)
	 */
	@Override
	public String update(@Valid E entity, BindingResult result, ModelMap model, @PathVariable PK id,
			HttpServletRequest request) {
		setCommonAttributes(model);
		incrementPreviousPage(model, request);
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
	 * @see
	 * br.com.cams7.app.controller.BaseController#destroy(java.io.Serializable)
	 */
	@Override
	public ResponseEntity<Map<String, String>> destroy(@PathVariable PK id) {
		Response response;

		try {
			getService().delete(id);
			response = getDeleteResponse();
		} catch (Exception e) {
			response = getErrorResponse(e);
		}

		return new ResponseEntity<Map<String, String>>(getOnlyMessage(response), response.getStatus());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseController#list(org.springframework.ui.
	 * ModelMap, java.lang.Integer, java.lang.String, java.lang.String,
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

	// protected final String redirectMainPage() {
	// return "redirect:/" + getMainPage();
	// }

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

	protected final void setCommonAttributes(ModelMap model) {
		setUsuarioLogado(model);
		setMainPage(model);
	}

	private void setMainPage(ModelMap model) {
		setMainPage(model, getMainPage());
	}

	protected final void setEditPage(ModelMap model) {
		model.addAttribute("edit", true);
	}

	private void setPreviousPage(ModelMap model, Integer previousPage) {
		model.addAttribute(PREVIOUS_PAGE, previousPage);
	}

	protected final void incrementPreviousPage(ModelMap model, final HttpServletRequest request) {
		setPreviousPage(model, Integer.valueOf(request.getParameter(PREVIOUS_PAGE)) + 1);
	}

	protected E getEntity(PK id) {
		E entity = getService().getById(id);
		return entity;
	}

	protected E getNewEntity() {
		E entity = AppHelper.getNewEntity(ENTITY_TYPE);
		return entity;
	}

	protected final String getMainPage() {
		return getModelName();
	}

	protected final String getIndexTilesPage() {
		return getModelName() + "_index";
	}

	protected final String getCreateTilesPage() {
		return getModelName() + "_create";
	}

	protected final String getShowTilesPage() {
		return getModelName() + "_show";
	}

	protected final String getEditTilesPage() {
		return getModelName() + "_edit";
	}

	protected final String getListTilesPage() {
		return getModelName() + "_list";
	}

	protected abstract String getModelName();

	protected abstract String getListName();

	protected abstract String[] getGlobalFilters();

	public static void setMainPage(ModelMap model, String mainPage) {
		model.addAttribute("mainPage", mainPage);
	}

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

	protected abstract String getDeleteMessage();

	/**
	 * @param e
	 *            Exception
	 * @return
	 */
	protected final Map<String, String> getOnlyMessage(Response response) {
		@SuppressWarnings("unchecked")
		Map<String, String> body = (Map<String, String>) (Map<String, ?>) response.getBody();
		return body;
	}

	protected final Response getDeleteResponse() {
		return getSucessResponse(null, getDeleteMessage());
	}

	protected final Response getSucessResponse(AbstractEntity<?> entity) {
		return getSucessResponse(entity, null);
	}

	protected final Response getErrorResponse(Exception e) {
		LOGGER.error(e.getMessage());

		Map<String, Serializable> response = getResponseBody(null, e.getMessage());
		HttpStatus status = INTERNAL_SERVER_ERROR;

		if (e instanceof AppException)
			status = ((AppException) e).getStatus();

		return new Response(response, status);
	}

	private Response getSucessResponse(AbstractEntity<?> entity, String message) {
		Map<String, Serializable> response = getResponseBody(entity, message);
		return new Response(response, OK);
	}

	private Map<String, Serializable> getResponseBody(AbstractEntity<?> entity, String message) {
		Map<String, Serializable> objectMessage = new HashMap<>();

		if (entity != null)
			objectMessage.put("entity", entity);
		if (message != null)
			objectMessage.put("message", message);

		return objectMessage;
	}

	private void setPreviousPage(final HttpServletRequest request) {
		final String PAGE = request.getHeader("Referer");
		final int INDEX = PAGE.indexOf("?");
		request.getSession().setAttribute(PREVIOUS_PAGE, PAGE.substring(0, INDEX > 0 ? INDEX : PAGE.length()));
	}

	protected final String redirectToPreviousPage(final HttpServletRequest request) {
		return "redirect:" + (String) request.getSession().getAttribute(PREVIOUS_PAGE);
	}

	private void sucessMessage(ModelMap model, String message) {
		model.addAttribute("sucessMessage", message);
	}

	protected final void sucessMessage(ModelMap model) {
		sucessMessage(model, getStoreSucessMessage());
	}

	protected final void sucessMessage(ModelMap model, E entity) {
		sucessMessage(model, getUpdateSucessMessage(entity));
	}

	protected abstract String getStoreSucessMessage();

	protected abstract String getUpdateSucessMessage(E entity);

	protected final MessageSource getMessageSource() {
		return messageSource;
	}

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

}
