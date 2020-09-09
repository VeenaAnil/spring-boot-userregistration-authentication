package com.staxter.uam.exception;

/**
 * 
 * This Custom Exception class to handle Custom Exception
 * @author Veena Anil
 *
 */
public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
