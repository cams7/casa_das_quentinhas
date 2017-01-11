/**
 * 
 */
package br.com.cams7.app.utils;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
public class AppInvalidDataException extends AppException {

	/**
	 * @param message
	 */
	public AppInvalidDataException(String message) {
		super(message, BAD_REQUEST);
	}

}
