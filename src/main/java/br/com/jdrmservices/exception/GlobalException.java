package br.com.jdrmservices.exception;

public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GlobalException(String message) {
		super(message);
	}
}
