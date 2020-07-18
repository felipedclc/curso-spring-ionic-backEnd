package com.felipedclc.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValitadionError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValitadionError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErrors() { // LISTA DE MENSAGENS 
		return errors;
	}

	public void addError(String fieldName, String message) { // CONSTRUTOR MANUAL (SET)
		errors.add(new FieldMessage(fieldName, message));
	}

	
}
