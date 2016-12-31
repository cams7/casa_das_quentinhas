/**
 * 
 */
package br.com.cams7.app.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cams7.app.model.AbstractEntity;
import br.com.cams7.app.service.BaseService;
import br.com.cams7.app.utils.AppHelper;
import br.com.cams7.app.utils.SearchParams;
import br.com.cams7.app.utils.SearchParams.SortOrder;

/**
 * @author César Magalhães
 *
 */
public abstract class AbstractController<S extends BaseService<E, PK>, E extends AbstractEntity<PK>, PK extends Serializable>
		implements BaseController<E, PK> {

	protected final Class<E> ENTITY_TYPE;

	protected final String LAST_LOADED_PAGE = "lastLoadedPage";
	private final short MAX_RESULTS = 10;

	@Autowired
	private S service;

	protected S getService() {
		return service;
	}

	@SuppressWarnings("unchecked")
	public AbstractController() {
		super();

		ENTITY_TYPE = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseController#index(org.springframework.ui.
	 * ModelMap)
	 */
	@Override
	public String index(ModelMap model) {
		Integer offset = 0;
		String sortField = "id";
		SortOrder sortOrder = SortOrder.DESCENDING;
		String query = "";

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sortOrder, null);

		List<E> entities = getService().search(params);
		long count = getService().count();

		model.addAttribute(getListName(), entities);

		setPaginationAttribute(model, offset, sortField, sortOrder, query, count);

		setUsuarioLogado(model);
		setActivePage(model);
		setMainPage(model);

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
	public String create(ModelMap model) {
		E entity = getNewEntity();

		model.addAttribute(getEntityName(), entity);

		setUsuarioLogado(model);
		setLastLoadedPage(model, 1);
		setMainPage(model);

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
	public String store(@Valid E entity, BindingResult result, ModelMap model,
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		setUsuarioLogado(model);
		incrementLastLoadedPage(model, lastLoadedPage);
		setMainPage(model);

		if (result.hasErrors())
			return getCreateTilesPage();

		getService().persist(entity, getUsername());

		return redirectMainPage();
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
		E entity = getEntity(id);

		model.addAttribute(getEntityName(), entity);

		setUsuarioLogado(model);
		setMainPage(model);

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
	public String edit(@PathVariable PK id, ModelMap model) {
		E entity = getEntity(id);

		model.addAttribute(getEntityName(), entity);

		setEditPage(model);
		setUsuarioLogado(model);
		setLastLoadedPage(model, 1);
		setMainPage(model);

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
			@RequestParam(value = LAST_LOADED_PAGE, required = true) Integer lastLoadedPage) {

		setEditPage(model);
		setUsuarioLogado(model);
		incrementLastLoadedPage(model, lastLoadedPage);
		setMainPage(model);

		if (result.hasErrors())
			return getEditTilesPage();

		getService().update(entity);

		return redirectMainPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.app.controller.BaseController#destroy(java.io.Serializable)
	 */
	@Override
	public ResponseEntity<Void> destroy(@PathVariable PK id) {
		// if (((Integer) id).equals(1))
		// return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);

		getService().delete(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
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
	public String list(ModelMap model, @RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "f", required = true) String sortField,
			@RequestParam(value = "s", required = true) String sortOrder,
			@RequestParam(value = "q", required = true) String query) {

		SortOrder sorting = SortOrder.get(sortOrder);

		Map<String, Object> filters = new HashMap<>();
		// and
		// filters.put("nome", query);
		// filters.put("sobrenome", query);
		// filters.put("email", query);

		// or
		filters.put(SearchParams.GLOBAL_FILTER, query);
		final String[] globalFilters = getGlobalFilters();

		SearchParams params = new SearchParams(offset, MAX_RESULTS, sortField, sorting, filters, globalFilters);

		List<E> entities = getService().search(params);
		long count = getService().getTotalElements(filters, globalFilters);

		model.addAttribute(getListName(), entities);

		setPaginationAttribute(model, offset, sortField, sorting, query, count);

		return getListTilesPage();
	}

	protected String redirectMainPage() {
		return "redirect:/" + getMainPage();
	}

	private void setPaginationAttribute(ModelMap model, Integer offset, String sortField, SortOrder sortOrder,
			String query, Long count) {
		model.addAttribute("offset", offset);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortOrder", sortOrder.getSorting());
		model.addAttribute("query", query);

		model.addAttribute("maxResults", MAX_RESULTS);
		model.addAttribute("count", count);
	}

	protected void setMainPage(ModelMap model) {
		model.addAttribute("mainPage", getMainPage());
	}

	private void setActivePage(ModelMap model) {
		model.addAttribute("activePage", getIndexTilesPage());
	}

	protected void setEditPage(ModelMap model) {
		model.addAttribute("edit", true);
	}

	protected void setUsuarioLogado(ModelMap model) {
		model.addAttribute("loggedinuser", getUsername());
	}

	private void setLastLoadedPage(ModelMap model, Integer lastLoadedPage) {
		model.addAttribute(LAST_LOADED_PAGE, lastLoadedPage);
	}

	protected void incrementLastLoadedPage(ModelMap model, Integer lastLoadedPage) {
		setLastLoadedPage(model, lastLoadedPage + 1);
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	protected String getUsername() {
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			username = ((UserDetails) principal).getUsername();
		else
			username = principal.toString();

		return username;
	}

	protected E getEntity(PK id) {
		E entity = getService().getById(id);
		return entity;
	}

	protected E getNewEntity() {
		E entity = AppHelper.getNewEntity(ENTITY_TYPE);
		return entity;
	}

	protected abstract String getEntityName();

	protected abstract String getListName();

	protected abstract String getMainPage();

	protected abstract String getIndexTilesPage();

	protected abstract String getCreateTilesPage();

	protected abstract String getShowTilesPage();

	protected abstract String getEditTilesPage();

	protected abstract String getListTilesPage();

	protected abstract String[] getGlobalFilters();

}
