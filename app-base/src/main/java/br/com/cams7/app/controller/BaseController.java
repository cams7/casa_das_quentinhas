/**
 * 
 */
package br.com.cams7.app.controller;

import java.io.Serializable;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.cams7.app.model.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
/**
 * @author César Magalhães
 *
 * @param <E>
 * @param <PK>
 */
public interface BaseController<E extends AbstractEntity<PK>, PK extends Serializable> {
	/**
	 * Display a listing of the resource.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(value = { "/list" })
	String index(ModelMap model);

	/**
	 * Show the form for creating a new resource.
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(value = { "/create" })
	String create(ModelMap model);

	/**
	 * Store a newly created resource in storage.
	 *
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping(value = { "/create" })
	String store(E entity, BindingResult result, ModelMap model, Integer lastLoadedPage);

	/**
	 * Display the specified resource.
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping(value = { "/{id}" })
	String show(PK id, ModelMap model);

	/**
	 * Show the form for editing the specified resource.
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping(value = { "/{id}/edit" })
	String edit(PK id, ModelMap model);

	/**
	 * Update the specified resource in storage.
	 *
	 * @param entity
	 * @param id
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping(value = { "/{id}/edit" })
	// @PutMapping
	String update(E entity, BindingResult result, ModelMap model, PK id, Integer lastLoadedPage);

	/**
	 * Remove the specified resource from storage.
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = { "/{id}/delete" })
	// @DeleteMapping
	String destroy(PK id);

	@GetMapping(value = "/pagination")
	String list(ModelMap model, Integer offset, String query);
}
