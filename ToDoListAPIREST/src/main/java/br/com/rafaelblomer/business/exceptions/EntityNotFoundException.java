package br.com.rafaelblomer.business.exceptions;

public class EntityNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -8694083974190690341L;

	public EntityNotFoundException(String msg) {
        super(msg);
    }
}
