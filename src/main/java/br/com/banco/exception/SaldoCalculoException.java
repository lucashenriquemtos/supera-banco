package br.com.banco.exception;

public class SaldoCalculoException extends CustomException{

	public SaldoCalculoException(String message) {
		super(message);
	}

	@Override
	protected String getCustomMessage() {
		return getMessage();
	}
}
