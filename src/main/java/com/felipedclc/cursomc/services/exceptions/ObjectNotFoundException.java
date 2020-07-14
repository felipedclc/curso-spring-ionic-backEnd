package com.felipedclc.cursomc.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {
		super(msg); // SUPERCLASSE - RunTimeException
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) { // RECEBE A CAUSA QUE ANTES CAUSOU A EXCEÇÃO
		super(msg, cause);
	}
}
