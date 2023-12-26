package com.example.customerandproduct.exception;

public class CustomerNotFoundException extends  RuntimeException{
	
	public CustomerNotFoundException(String message) {
		super(message);
	}
	public CustomerNotFoundException() {
		
	}
}
