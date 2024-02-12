package com.note.notetutorial.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;
import org.springframework.lang.Nullable;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.note.notetutorial.model.HttpResponse;

import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;

import java.time.LocalDateTime;
import static com.note.notetutorial.util.DateUtil.dateTimeFormatter;
import java.util.stream.Collectors;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

//import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
@Slf4j
public class HandleException extends ResponseEntityExceptionHandler implements ErrorController {

//	@Nullable
//	protected ResponseEntity<Object> handleExceptionInternal(
//			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.error(ex.getMessage());
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(", "));
		
		//return handleExceptionInternal(ex, null, headers, status, request);
		return new ResponseEntity<>(
				HttpResponse.builder()
				.reason("Invalid fields: " + fieldsMessage)
				.developerMessage(ex.getMessage())
				.status(status)
				.statusCode(status.value())
				.timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
				.build(), 
				status);
	}
	
	protected ResponseEntity<Object> handleMethodArgumentNotValidOrig(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.error(exception.getMessage());
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(", "));
		return new ResponseEntity<>(HttpResponse.builder()
				.reason("Invalid fields: " + fieldsMessage)
				.developerMessage(exception.getMessage())
				.status(status)
				.statusCode(status.value())
				.timeStamp(LocalDateTime.now().format(dateTimeFormatter())).build(), status);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception, 
			@Nullable Object body,
			HttpHeaders headers, 
			HttpStatusCode status, 
			WebRequest request) {
		
		log.error(exception.getMessage());
		
		return new ResponseEntity<>(HttpResponse.builder()
				.reason(exception.getMessage())
				.developerMessage(exception.getMessage())
				.status(status)
				.statusCode(status.value())
				.timeStamp(LocalDateTime.now()
						.format(dateTimeFormatter()))
				.build(), status);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<HttpResponse<?>> illegalStateException(IllegalStateException exception) {
		return createHttpErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(),
				exception);
	}

	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<HttpResponse<?>> noteNotFoundException(NoteNotFoundException exception) {
		return createHttpErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(),
				exception);
	}

	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<HttpResponse<?>> noResultException(NoResultException exception) {
		return createHttpErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage(), exception);
	}

	@ExceptionHandler(ServletException.class)
	public ResponseEntity<HttpResponse<?>> servletException(ServletException exception) {
		return createHttpErrorResponse(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage(), exception);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<HttpResponse<?>> exception(Exception exception) {
		return createHttpErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage(), exception);
	}

	private ResponseEntity<HttpResponse<?>> createHttpErrorResponse(HttpStatusCode httpStatus, String reason,
			Exception exception) {
		
		log.error(exception.getMessage());
		
		return new ResponseEntity<>(HttpResponse.builder()
				.reason(reason)
				.developerMessage(exception.getMessage())
				.status(httpStatus)
				.statusCode(httpStatus.value())
				.timeStamp(LocalDateTime.now()
						.format(dateTimeFormatter()))
				.build(),
				httpStatus);
	}
}
