package com.algaworks.brewer.services.exception;

public class ObjetoJaExisteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public ObjetoJaExisteException(String message) {
        super(message);
    }
}
