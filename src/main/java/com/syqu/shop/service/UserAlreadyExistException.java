package com.syqu.shop.service;

public class UserAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(final String msg) {
		   super(msg);
	}
}
