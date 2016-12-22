/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cams7.casa_das_quentinhas.model.AbstractEntity;
import br.com.cams7.casa_das_quentinhas.service.BaseService;
import br.com.cams7.casa_das_quentinhas.utils.AppHelper;
import br.com.cams7.casa_das_quentinhas.utils.SearchParams;

/**
 * @author César Magalhães
 *
 */
public abstract class AbstractController<S extends BaseService<E, PK>, E extends AbstractEntity<PK>, PK extends Serializable>
		implements BaseController<E, PK> {

	protected final Class<E> ENTITY_TYPE;

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
	 * br.com.cams7.casa_das_quentinhas.controller.BaseController#index(org.
	 * springframework.ui.ModelMap)
	 */
	@Override
	public String index(ModelMap model) {
		Integer offset = 0;
		Integer maxResults = 10;

		SearchParams params = new SearchParams(offset, maxResults.shortValue(), null, null, null);

		List<E> entities = getService().search(params);
		Long count = getService().count();

		model.addAttribute(getListName(), entities);
		setPaginationAttribute(model, offset, count);

		model.addAttribute("loggedinuser", getPrincipal());

		return getIndexTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.BaseController#create(org.
	 * springframework.ui.ModelMap)
	 */
	@Override
	public String create(ModelMap model) {
		E entity = getNewEntity();

		model.addAttribute(getEntityName(), entity);
		model.addAttribute("loggedinuser", getPrincipal());

		return getCreateTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.BaseController#store(br.com.
	 * cams7.casa_das_quentinhas.model.AbstractEntity,
	 * org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap)
	 */
	@Override
	public String store(@Valid E entity, BindingResult result, ModelMap model) {
		if (result.hasErrors())
			return getCreateTilesPage();

		getService().persist(entity);

		// model.addAttribute("success",
		// "User " + usuario.getNome() + " " + usuario.getSobrenome() + "
		// registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());

		return getMainPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.BaseController#show(java.io.
	 * Serializable, org.springframework.ui.ModelMap)
	 */
	@Override
	public String show(@PathVariable PK id, ModelMap model) {
		E entity = getEntity(id);

		model.addAttribute(getEntityName(), entity);
		model.addAttribute("loggedinuser", getPrincipal());

		return getShowTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.BaseController#edit(java.io.
	 * Serializable, org.springframework.ui.ModelMap)
	 */
	@Override
	public String edit(@PathVariable PK id, ModelMap model) {
		E entity = getEntity(id);

		model.addAttribute(getEntityName(), entity);
		model.addAttribute("loggedinuser", getPrincipal());

		model.addAttribute("edit", true);

		return getEditTilesPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.BaseController#update(br.com.
	 * cams7.casa_das_quentinhas.model.AbstractEntity,
	 * org.springframework.validation.BindingResult,
	 * org.springframework.ui.ModelMap, java.io.Serializable)
	 */
	@Override
	public String update(@Valid E entity, BindingResult result, ModelMap model, @PathVariable PK id) {
		model.addAttribute("edit", true);

		if (result.hasErrors())
			return getEditTilesPage();

		getService().update(entity);

		// model.addAttribute("success",
		// "User " + entity.getNome() + " " + entity.getSobrenome() + " updated
		// successfully");
		model.addAttribute("loggedinuser", getPrincipal());

		return getMainPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.casa_das_quentinhas.controller.BaseController#destroy(java.
	 * io.Serializable)
	 */
	@Override
	public String destroy(@PathVariable PK id) {
		getService().delete(id);
		return getMainPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.cams7.casa_das_quentinhas.controller.BaseController#list(org.
	 * springframework.ui.ModelMap, java.lang.Integer, java.lang.String)
	 */
	@Override
	public String list(ModelMap model, @RequestParam("offset") Integer offset, @RequestParam("q") String query) {
		Integer maxResults = 10;

		SearchParams params = new SearchParams(offset, maxResults.shortValue(), null, null, null);

		List<E> entities = getService().search(params);
		Long count = getService().count();

		model.addAttribute(getListName(), entities);

		setPaginationAttribute(model, offset, count);

		return getListTilesPage();
	}

	private void setPaginationAttribute(ModelMap model, Integer offset, Long count) {
		model.addAttribute("count", count);
		model.addAttribute("offset", offset);
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

}
