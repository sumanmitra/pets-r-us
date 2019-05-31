package com.petsrus.exception;

public class ResourceNotFoundException extends Exception{
	
	private static final long serialVersionUID = -123456789L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(final String message) {
		super(message);
	}


}
