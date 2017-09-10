package com.mcloud.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found !")
public class CustomInvalidGrantException extends InvalidGrantException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomInvalidGrantException(String msg) {
		super(msg);
	}
}
