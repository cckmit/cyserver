package com.cy.shortmessage.exception;

public class ShortMessageException extends Exception {
	private static final long serialVersionUID = 1L;

	public ShortMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ShortMessageException(Throwable cause) {
		super(cause);
	}

}
