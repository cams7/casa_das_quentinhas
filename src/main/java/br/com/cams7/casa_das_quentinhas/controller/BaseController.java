/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import javax.validation.Valid;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import br.com.cams7.casa_das_quentinhas.model.AbstractEntity;

/**
 * @author César Magalhães
 *
 */
public interface BaseController<E extends AbstractEntity<?>> {
	String index(ModelMap model);

	String create(ModelMap model);

	String store(@Valid E entity, BindingResult result, ModelMap model);
}
