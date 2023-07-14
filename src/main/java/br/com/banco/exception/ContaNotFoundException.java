package br.com.banco.exception;

public class ContaNotFoundException extends CustomException {
	public ContaNotFoundException(String message) {
		super(message);
	}

	@Override
	protected String getCustomMessage() {
		return super.getMessage();
	}
}
