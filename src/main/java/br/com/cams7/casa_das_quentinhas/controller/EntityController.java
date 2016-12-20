/**
 * 
 */
package br.com.cams7.casa_das_quentinhas.controller;

import javax.validation.Valid;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import br.com.cams7.casa_das_quentinhas.entity.UsuarioEntity;

/**
 * @author César Magalhães
 *
 */
public interface EntityController {
	String index(ModelMap model);

	String create(ModelMap model);

	String store(@Valid UsuarioEntity usuarioEntity, BindingResult result, ModelMap model);
}
