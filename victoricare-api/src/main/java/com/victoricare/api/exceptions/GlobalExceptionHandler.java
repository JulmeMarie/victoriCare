package com.victoricare.api.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.data.mapping.PropertyReferenceException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	public ResponseEntity<ErrorMessage> resourceNotFoundException(RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { ResourceNotModifiedException.class })
	public ResponseEntity<ErrorMessage> resourceNotUpdatedException(RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_MODIFIED.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.NOT_MODIFIED);
	}

	@ExceptionHandler(value = { ActionNotAuthorizedException.class })
	public ResponseEntity<ErrorMessage> resourceNotAvailableException(RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.UNAUTHORIZED.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = { ActionNotAllowedException.class })
	public ResponseEntity<ErrorMessage> actionForbiddenException(RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.FORBIDDEN.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = { InvalidInputException.class })
	public ResponseEntity<ErrorMessage> invalidInputException(RuntimeException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class, InternalServerException.class, PropertyReferenceException.class })
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
