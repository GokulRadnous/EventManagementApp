package com.eventmanagement.exception;

public class IdNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4470372468520045614L;

	public IdNotFoundException(String message) {
		super(message);
	}
}
