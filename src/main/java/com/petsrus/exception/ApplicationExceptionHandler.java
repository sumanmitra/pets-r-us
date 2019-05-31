package com.petsrus.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleNumberFormatException(final NumberFormatException exception,
			final HttpServletRequest request) {

		return generateExceptionResponse(exception, request,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleResourceNotFound(final ResourceNotFoundException exception,
			final HttpServletRequest request) {

		return generateExceptionResponse(exception, request,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleConstraintViolationException(
			final DataIntegrityViolationException exception, final HttpServletRequest request) {

		return generateExceptionResponse(exception, request,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
			final HttpRequestMethodNotSupportedException exception, final HttpServletRequest request) {

		return generateExceptionResponse(exception, request,HttpStatus.METHOD_NOT_ALLOWED);
	}
	

	
	@ExceptionHandler(InvalidDefinitionException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleInvalidDefinitionException(
			final InvalidDefinitionException exception, final HttpServletRequest request) {

		return generateExceptionResponse(exception, request,HttpStatus.BAD_REQUEST);
	}
	
	
	
	

	private ResponseEntity<Object> generateExceptionResponse(final Exception exception,
			final HttpServletRequest request, HttpStatus httpStatus, String...customMessage) {
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage((customMessage.length>0?customMessage[0]:"") + exception.getLocalizedMessage());
		error.callerURL(request.getRequestURI());
		error.setStatus(httpStatus);
		
		return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
	}
	
	
/*	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleHttpMessageNotReadableException(
			final HttpMessageNotReadableException exception, final HttpServletRequest request) {

		return generateExceptionResponse(exception, request,HttpStatus.BAD_REQUEST, "Invalid type :");
	}*/

	

}
