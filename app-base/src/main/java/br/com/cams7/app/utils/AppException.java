/**
 * 
 */
package br.com.cams7.app.utils;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.http.HttpStatus;

/**
 * Exceção padrão da aplicação
 * 
 * @author cesar
 */
@SuppressWarnings("serial")
public class AppException extends RuntimeException {

	private HttpStatus status;

	public AppException(String message) {
		super(message);
		status = BAD_REQUEST;
	}

	/**
	 * @param message
	 * @param status
	 */
	protected AppException(String message, HttpStatus status) {
		this(message);

		this.status = status;
	}

	public final HttpStatus getStatus() {
		return status;
	}

}
