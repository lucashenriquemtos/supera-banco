package br.com.banco.exception;

public abstract class CustomException extends RuntimeException {

	public CustomException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return getCustomMessage();
	}

	protected abstract String getCustomMessage();
}

