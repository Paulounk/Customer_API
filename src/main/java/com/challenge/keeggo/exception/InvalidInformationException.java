package com.challenge.keeggo.exception;

public class InvalidInformationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidInformationException(String message) {
		super(message);
	};
}
