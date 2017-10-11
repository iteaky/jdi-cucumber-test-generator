package com.epam.test_generator.controllers;

import com.epam.test_generator.services.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleRunTimeException(Exception ex) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Void> handleNotFoundException(NotFoundException ex){
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}