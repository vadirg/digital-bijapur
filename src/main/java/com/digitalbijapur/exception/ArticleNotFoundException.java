package com.digitalbijapur.exception;

public class ArticleNotFoundException extends Exception {

	public ArticleNotFoundException() {
	}

	public ArticleNotFoundException(String message) {
		super(message);
	}

	public ArticleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
