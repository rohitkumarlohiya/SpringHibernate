package com.mcloud.exceptions.custom;

public class BadRequestException extends RuntimeException {

	private String errorString;

	/**
	 * @author Nagesh.Chauhan
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String errorString) {
		this.errorString = errorString;
	}

	@Override
	public String toString() {
		return "Exception : " + errorString;
	}

}
