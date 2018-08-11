package com.digitalbijapur.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {

	public ArticleNotFoundException() {
	}

	public ArticleNotFoundException(String message) {
		super(message);
	}

	public ArticleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
