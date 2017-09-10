package com.mcloud.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Entity not found !")
@Component
public class SomethingWentWrongException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
