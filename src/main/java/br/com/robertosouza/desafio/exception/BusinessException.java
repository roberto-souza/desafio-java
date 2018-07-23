package br.com.robertosouza.desafio.exception;

public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -2631626153090970372L;

	public BusinessException() {}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
}
