package com.felipedclc.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidadionError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidadionError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() { // LISTA DE MENSAGENS 
		return errors;
	}

	public void addError(String fieldName, String message) { // CONSTRUTOR MANUAL (SET)
		errors.add(new FieldMessage(fieldName, message));
	}

	
}
