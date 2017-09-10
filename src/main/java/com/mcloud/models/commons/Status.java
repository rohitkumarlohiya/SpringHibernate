package com.mcloud.models.commons;

import java.util.List;
import java.util.UUID;

public class Status {
	private String code;
	private String message;
	private Long updatedId;
	private UUID uuId;
	private List<Errors> validationErrors;

	public Status() {
	}

	public Status(UUID uuId, String code, String message) {
		this.uuId = uuId;
		this.code = code;
		this.message = message;
	}

	public Status(Long updatedId, String code, String message) {
		this.updatedId = updatedId;
		this.code = code;
		this.message = message;
	}

	public Status(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public Status(String code, String message, List<Errors> validationErrors) {
		this.code = code;
		this.message = message;
		this.validationErrors = validationErrors;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Errors> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<Errors> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public Long getUpdatedId() {
		return updatedId;
	}

	public void setUpdatedId(Long updatedId) {
		this.updatedId = updatedId;
	}

	public UUID getUuId() {
		return uuId;
	}

	public void setUuId(UUID uuId) {
		this.uuId = uuId;
	}
}
