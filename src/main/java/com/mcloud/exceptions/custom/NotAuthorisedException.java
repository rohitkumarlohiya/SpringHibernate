package com.mcloud.exceptions.custom;

public class NotAuthorisedException extends RuntimeException {

	private String errorString;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotAuthorisedException() {
		super();
	}

	public NotAuthorisedException(String errorString) {
		this.errorString = errorString;
	}

	@Override
	public String toString() {
		return "Exception : " + errorString;
	}
}
