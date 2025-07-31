package com.eventmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eventmanagement.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException exception) {
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Failure");
		response.setData(exception.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNoRecordAvailable(NoRecordAvailableException exception) {
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No Record");
		response.setData(exception.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}
}
