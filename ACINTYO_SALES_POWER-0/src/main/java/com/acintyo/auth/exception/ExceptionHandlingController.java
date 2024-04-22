package com.acintyo.auth.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.acintyo.auth.response.ErrorDetails;

@RestControllerAdvice
public class ExceptionHandlingController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handlingValidationException(MethodArgumentNotValidException me) {
		HashMap<String, String> hashMap = new HashMap<>();
		me.getFieldErrors().forEach(x -> hashMap.put(x.getField(), x.getDefaultMessage()));
		return hashMap;
	}

	@ExceptionHandler(MrVisitsNotFoundException.class)
	public ResponseEntity<?> handlingDataNotFoundException(MrVisitsNotFoundException de, WebRequest request) {
		return new ResponseEntity<>(
				new ErrorDetails(HttpStatus.NO_CONTENT.value(), de.getMessage(), request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handlingIllegalArgumentException(IllegalArgumentException ie, WebRequest request) {
		return new ResponseEntity<>(
				new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ie.getMessage(), request.getDescription(false)),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> handlingRuntimeException(RuntimeException re, WebRequest request) {
		return new ResponseEntity<>(new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), re.getMessage(),
				request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
