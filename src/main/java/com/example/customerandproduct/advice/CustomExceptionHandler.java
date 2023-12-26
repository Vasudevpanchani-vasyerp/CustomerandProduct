package com.example.customerandproduct.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.customerandproduct.dto.CustomFormatDTO;
import com.example.customerandproduct.exception.CustomerNotFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(CustomerNotFoundException.class)
	public CustomFormatDTO CustomerNotFoundExceptionHandling(CustomerNotFoundException ex)
	{
		return new CustomFormatDTO(404,"Error",ex.getMessage());
	}
	
}
