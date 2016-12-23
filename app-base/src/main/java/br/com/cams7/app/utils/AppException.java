/**
 * 
 */
package br.com.cams7.app.utils;

import java.util.Map;

/**
 * Exceção padrão da aplicação
 * 
 * @author cesar
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Map<String, String> messages;

	public AppException() {
		super();
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AppException(Map<String, String> messages) {
		super();
		this.messages = messages;
	}

	public Map<String, String> getMessages() {
		return messages;
	}

}
