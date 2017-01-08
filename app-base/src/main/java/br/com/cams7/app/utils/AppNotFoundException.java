/**
 * 
 */
package br.com.cams7.app.utils;

import java.util.Map;

/**
 * @author César Magalhães
 *
 */
@SuppressWarnings("serial")
public class AppNotFoundException extends AppException {

	/**
	 * 
	 */
	public AppNotFoundException() {
		super();
	}

	/**
	 * @param message
	 */
	public AppNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AppNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AppNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AppNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param messages
	 */
	public AppNotFoundException(Map<String, String> messages) {
		super(messages);
	}

}
