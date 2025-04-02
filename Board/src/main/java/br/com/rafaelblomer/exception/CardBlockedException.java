package br.com.rafaelblomer.exception;

public class CardBlockedException extends RuntimeException {
	private static final long serialVersionUID = -8350931418783061754L;

	public CardBlockedException(String msg) {
		super(msg);
	}
}
