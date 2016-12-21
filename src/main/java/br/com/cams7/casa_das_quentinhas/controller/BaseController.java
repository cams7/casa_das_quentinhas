/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import java.io.Serializable;

//import javax.validation.Valid;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PathVariable;

import br.com.cams7.casa_das_quentinhas.model.AbstractEntity;

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
	String index(ModelMap model);

	/**
	 * Show the form for creating a new resource.
	 *
	 * @param model
	 * @return
	 */
	String create(ModelMap model);

	/**
	 * Store a newly created resource in storage.
	 *
	 * @param entity
	 * @param result
	 * @param model
	 * @return
	 */
	String store(/* @Valid */ E entity, BindingResult result, ModelMap model);

	/**
	 * Display the specified resource.
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	String show(/* @PathVariable */ PK id, ModelMap model);

	/**
	 * Show the form for editing the specified resource.
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	String edit(/* @PathVariable */ PK id, ModelMap model);

	/**
	 * Update the specified resource in storage.
	 *
	 * @param entity
	 * @param id
	 * @param result
	 * @param model
	 * @return
	 */
	String update(/* @Valid */ E entity, BindingResult result, ModelMap model, /* @PathVariable */ PK id);

	/**
	 * Remove the specified resource from storage.
	 *
	 * @param id
	 * @return
	 */
	String destroy(/* @PathVariable */ PK id);
}
