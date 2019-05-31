package com.petsrus.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

	private String errorMessage;
	private String requestedURI;
	private HttpStatus status;


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRequestedURI() {
		return requestedURI;
	}

	public void callerURL(final String requestedURI) {
		this.requestedURI = requestedURI;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}