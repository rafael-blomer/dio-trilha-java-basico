package br.com.rafaelblomer.exception;

public class CardFinishedException extends RuntimeException {
	private static final long serialVersionUID = -5849401464272633494L;

	public CardFinishedException(String msg) {
		super(msg);
	}
}
