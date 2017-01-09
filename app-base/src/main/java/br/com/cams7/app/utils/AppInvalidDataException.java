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
public class AppInvalidDataException extends AppException {

	/**
	 * 
	 */
	public AppInvalidDataException() {
		super();
	}

	/**
	 * @param message
	 */
	public AppInvalidDataException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AppInvalidDataException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AppInvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AppInvalidDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param messages
	 */
	public AppInvalidDataException(Map<String, String> messages) {
		super(messages);
	}

}
