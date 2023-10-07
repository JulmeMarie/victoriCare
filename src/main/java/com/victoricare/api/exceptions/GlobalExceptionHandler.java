package com.victoricare.api.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {

	@ExceptionHandler(value = {ResourceNotFoundException.class})
	  public ResponseEntity<ErrorMessage> resourceNotFoundException(RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_FOUND.value(),
		        new Date(),
		        ex.getMessage(),
		        request.getDescription(false));

	    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	  }

	@ExceptionHandler(value = {ResourceNotAllowedException.class, AuthException.class})
	  public ResponseEntity<ErrorMessage> resourceNotAvailableException(RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(),
		        new Date(),
		        ex.getMessage(),
		        request.getDescription(false));

	    return new ResponseEntity<>(message, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
	  }

	@ExceptionHandler(value = {ResourceNotUpdatedException.class, ResourceNotCreatedException.class})
	  public ResponseEntity<ErrorMessage> resourceNotUpdatedException( RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_MODIFIED.value(),
		        new Date(),
		        ex.getMessage(),
		        request.getDescription(false));

	    return new ResponseEntity<>(message, HttpStatus.NOT_MODIFIED);
	}

	@ExceptionHandler(value = {Exception.class, UnknownException.class })
	  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
	    ErrorMessage message = new ErrorMessage(
	        HttpStatus.INTERNAL_SERVER_ERROR.value(),
	        new Date(),
	        ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
}
