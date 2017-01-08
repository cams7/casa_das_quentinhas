/**
 * 
 */
package br.com.cams7.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cams7.app.utils.AppNotFoundException;

/**
 * @author César Magalhães
 *
 */
@ControllerAdvice
public class GlobalExceptionHandlerController {

	private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

	@ExceptionHandler(value = AppNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException(AppNotFoundException e) {
		LOGGER.warn(e.getMessage());
		return "nao_encontrado";
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleAllException(Exception e) {
		LOGGER.error(e.getMessage());
		return "erro";
	}
}
