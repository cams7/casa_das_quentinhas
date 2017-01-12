/**
 * 
 */
package br.com.cams7.app;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
public class AppNotFoundException extends AppException {

	/**
	 * @param message
	 */
	public AppNotFoundException(String message) {
		super(message, NOT_FOUND);
	}

}
