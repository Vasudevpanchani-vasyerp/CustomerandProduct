package com.example.customerandproduct.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.customerandproduct.dto.CustomFormatDTO;
import com.example.customerandproduct.dto.CustomerDTO;
import com.example.customerandproduct.model.Customer;

public interface CustomerService {
	
	CustomFormatDTO getAllDetails();
	CustomFormatDTO addCustomer(Customer customer);
	/* CustomFormatDTO deleteCustomer(long customerid); */
	Customer deleteCustomerById(long id);
	CustomFormatDTO updateCustomer(Customer customer);
	CustomFormatDTO addDataById(long customerid,long productid);
	Customer CustomerFindById(long id);
}
