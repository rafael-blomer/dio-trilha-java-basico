package br.com.rafaelblomer.exception;

public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5404679312753607856L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}
}
