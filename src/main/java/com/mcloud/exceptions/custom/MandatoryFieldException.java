package com.mcloud.exceptions.custom;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mcloud.models.commons.Errors;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Mandatory Fields Required")
@Component
public class MandatoryFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Errors[] errors;

	public MandatoryFieldException() {
	}

	public MandatoryFieldException(Errors... errors) {
		this.errors = errors;
	}

	public List<Errors> getErrors() {
		return Arrays.asList(errors);
	}
}
