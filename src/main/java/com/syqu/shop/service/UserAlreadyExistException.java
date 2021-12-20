package com.syqu.shop.service;

public class UserAlreadyExistException extends Exception {

	public UserAlreadyExistException(final String msg) {
		   super(msg);
	}
}
