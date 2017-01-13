/**
 * 
 */
package br.com.cams7.app;

import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Exceção padrão da aplicação
 * 
 * @author cesar
 */
@SuppressWarnings("serial")
public abstract class AppException extends RuntimeException {

	private HttpStatus status;
	private Map<String, String> messages;

	/**
	 * @param message
	 * @param status
	 */
	protected AppException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	/**
	 * @param messages
	 * @param status
	 */
	protected AppException(Map<String, String> messages, HttpStatus status) {
		this.messages = messages;
		this.status = status;
	}

	public final HttpStatus getStatus() {
		return status;
	}

	public Map<String, String> getMessages() {
		return messages;
	}

}
