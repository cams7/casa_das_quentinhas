/**
 * 
 */
package br.com.cams7.app;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Exceção padrão da aplicação
 * 
 * @author cesar
 */
@SuppressWarnings("serial")
public class AppException extends RuntimeException {

	private HttpStatus status;
	private Map<String, String> messages;

	public AppException() {
		super();
		status = BAD_REQUEST;
	}

	public AppException(String message) {		
		super(message);
		status = BAD_REQUEST;
	}

	public AppException(Map<String, String> messages) {
		this();
		this.messages = messages;
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

	public Map<String, String> getMessages() {
		return messages;
	}

}
