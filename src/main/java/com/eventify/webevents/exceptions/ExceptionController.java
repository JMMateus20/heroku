package com.eventify.webevents.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> returnResponseStatusException(ResponseStatusException e){
		String mensaje=e.getReason();
		return ResponseEntity.badRequest().body(mensaje);
	}
	
	
	@ExceptionHandler(ParseExceptionManaged.class)
	public ResponseEntity<String> returnParseException(ParseExceptionManaged e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
