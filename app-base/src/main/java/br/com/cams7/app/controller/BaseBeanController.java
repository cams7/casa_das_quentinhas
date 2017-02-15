/**
 * 
 */
package br.com.cams7.app.controller;

import static org.springframework.http.HttpStatus.OK;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cams7.app.entity.AbstractEntity;

/**
 * @author César Magalhães
 *
 * @param <PK>
 *            ID da entidade
 * @param <E>
 *            Entidade
 */
public interface BaseBeanController<PK extends Serializable, E extends AbstractEntity<PK>> {
	/**
	 * Display a listing of the resource.
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping
	@ResponseStatus(OK)
	String index(ModelMap model, HttpServletRequest request);

	/**
	 * Show the form for creating a new resource.
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/create")
	@ResponseStatus(OK)
	String create(ModelMap model, HttpServletRequest request);

	/**
	 * Store a newly created resource in storage.
	 *
	 * @param entity
	 *            Entidade
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/create")
	String store(E entity, BindingResult result, ModelMap model, HttpServletRequest request);

	/**
	 * Display the specified resource.
	 *
	 * @param id
	 *            ID da entidade
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(OK)
	String show(PK id, ModelMap model, HttpServletRequest request);

	/**
	 * Show the form for editing the specified resource.
	 *
	 * @param id
	 *            ID da entidade
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/{id}/edit")
	@ResponseStatus(OK)
	String edit(PK id, ModelMap model, HttpServletRequest request);

	/**
	 * Update the specified resource in storage.
	 *
	 * @param entity
	 *            Entidade
	 * @param result
	 * @param model
	 * @param id
	 *            ID da entidade
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/{id}/edit")
	String update(E entity, BindingResult result, ModelMap model, PK id, HttpServletRequest request);

	/**
	 * Remove the specified resource from storage.
	 *
	 * @param id
	 *            ID da entidade
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/{id}/delete")
	@ResponseBody
	ResponseEntity<Map<String, String>> destroy(PK id, HttpServletRequest request);

	/**
	 * @param model
	 * @param offset
	 * @param sortField
	 * @param sortOrder
	 * @param query
	 * @return
	 */
	@GetMapping(value = "/list")
	@ResponseStatus(OK)
	String list(ModelMap model, Integer offset, String sortField, String sortOrder, String query);
}
