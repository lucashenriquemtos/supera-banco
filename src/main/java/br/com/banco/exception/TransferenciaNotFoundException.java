package br.com.banco.exception;

public class TransferenciaNotFoundException extends CustomException {
	public TransferenciaNotFoundException(String message) {
		super(message);
	}

	@Override
	protected String getCustomMessage() {
		return getMessage();
	}
}
