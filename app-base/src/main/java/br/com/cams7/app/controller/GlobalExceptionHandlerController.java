/**
 * 
 */
package br.com.cams7.app.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import br.com.cams7.app.AppException;
import br.com.cams7.app.AppInvalidDataException;
import br.com.cams7.app.AppNotFoundException;

/**
 * @author César Magalhães
 *
 */
@ControllerAdvice
public class GlobalExceptionHandlerController {

	private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

	@ExceptionHandler(value = AppNotFoundException.class)
	@ResponseStatus(NOT_FOUND)
	public final ModelAndView handleNotFoundException(AppNotFoundException e) {
		return handleAppException(e);
	}

	@ExceptionHandler(value = AppInvalidDataException.class)
	@ResponseStatus(BAD_REQUEST)
	public final ModelAndView handleInvalidDataException(AppInvalidDataException e) {
		return handleAppException(e);
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = INTERNAL_SERVER_ERROR)
	public final ModelAndView handleAllException(Exception e) {
		ModelAndView model = new ModelAndView();
		model.addObject("message", e.getMessage());
		model.setViewName(INTERNAL_SERVER_ERROR.name().toLowerCase());
		return model;
	}

	/**
	 * @param e
	 * @return
	 */
	private ModelAndView handleAppException(AppException e) {
		ModelAndView model = new ModelAndView();

		if (e.getMessages() != null) {
			e.getMessages().forEach((key, value) -> {
				LOGGER.warn("{} - {}", key, value);
			});
			model.addObject("messages", e.getMessages());
		} else {
			LOGGER.warn(e.getMessage());
			model.addObject("message", e.getMessage());
		}

		model.setViewName(e.getStatus().name().toLowerCase());
		return model;
	}

}
