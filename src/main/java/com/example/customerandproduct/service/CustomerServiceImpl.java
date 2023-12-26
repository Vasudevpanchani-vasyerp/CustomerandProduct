package com.example.customerandproduct.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.customerandproduct.dto.CustomFormatDTO;
import com.example.customerandproduct.dto.CustomerDTO;
import com.example.customerandproduct.exception.CustomerNotFoundException;
import com.example.customerandproduct.model.Customer;
import com.example.customerandproduct.model.Product;
import com.example.customerandproduct.repository.CustomerRepository;
import com.example.customerandproduct.repository.ProductRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	private CustomFormatDTO response = new CustomFormatDTO();

	private void setCustomFormatDTO(int status, String message, Object ok) {
		response.setStatus(status);
		response.setMessage(message);
		response.setData(ok);
	}

	@Override
	public CustomFormatDTO getAllDetails() {
		/*
		 * List<CustomerDTO> customerDTOs=new ArrayList<>(); List<Customer>
		 * customers=customerRepository.findAll(); for(Customer c:customers) {
		 * CustomerDTO customerDTO=new CustomerDTO();
		 * customerDTO.setCustomerId(c.getCustomerId());
		 * customerDTO.setCustomerName(c.getCustomerName());
		 * customerDTO.setCustomerEmail(c.getCustomerEmail());
		 * customerDTO.setCustomerNumber(c.getCustomerNumber());
		 * customerDTO.setProduct(c.getProduct()); customerDTOs.add(customerDTO); }
		 * return customerDTOs;
		 */

		setCustomFormatDTO(200, "Data fetched succesfully", customerRepository.findAll());
		System.out.println(response);
		return response;
	}

	@Override
	public CustomFormatDTO addCustomer(Customer customer) {
		/*
		 * if(customerdto.getCustomerId()==0) { Customer customer=new Customer();
		 * customer.setCustomerId(customerdto.getCustomerId());
		 * customer.setCustomerName(customerdto.getCustomerName());
		 * customer.setCustomerEmail(customerdto.getCustomerEmail());
		 * customer.setCustomerNumber(customerdto.getCustomerNumber()); return
		 * customerRepository.save(customer); }else { Customer
		 * editCustomer=customerRepository.findById(customerdto.getCustomerId()).get();
		 * editCustomer.setCustomerId(customerdto.getCustomerId());
		 * editCustomer.setCustomerName(customerdto.getCustomerName());
		 * editCustomer.setCustomerEmail(customerdto.getCustomerEmail());
		 * editCustomer.setCustomerNumber(customerdto.getCustomerNumber()); return
		 * customerRepository.save(editCustomer); }
		 */
		try {
			Customer customers = customerRepository.save(customer);
			setCustomFormatDTO(200, "Data saved succesfully", customers);
		} catch (Exception e) {
			setCustomFormatDTO(404, "Data cann't saved", "Data can not be saved to database due to technical error");
			e.printStackTrace();
		}
		return response;
	}

	/*
	 * @Override public void deleteCustomer(long customerid) {
	 * customerRepository.deleteById(customerid); }
	 */

	@Override
	public CustomFormatDTO updateCustomer(Customer customer) {
		Customer editCustomer = customerRepository.findById(customer.getCustomerId()).orElse(null);
		if (editCustomer != null) {
			editCustomer.setCustomerId(customer.getCustomerId());
			editCustomer.setCustomerName(customer.getCustomerName());
			editCustomer.setCustomerEmail(customer.getCustomerEmail());
			editCustomer.setCustomerNumber(customer.getCustomerNumber());
			customerRepository.save(editCustomer);
			setCustomFormatDTO(200, "Data updated", editCustomer);
		}else {
			setCustomFormatDTO(400, "Data cann't be update", "Data can not be deleted because of some technical issue");
		}
		return response;
	}

	@Override
	public CustomFormatDTO addDataById(long customerid, long productid) {
		Customer customer = customerRepository.findById(customerid).get();
		Product product=productRepository.findById(productid).get();
		if (customer != null && product != null) {
			product.setProductId(productid);
			customer.setProduct(product);
			customerRepository.save(customer);
			setCustomFormatDTO(200, "The Customer Data set", customer);
		} else {
			if (customer == null) {
				setCustomFormatDTO(404, "The Customer Data not found", "customer not found");
			} else if (product == null) {
				setCustomFormatDTO(404, "The product Data not found", "product not found");
			} else {
				setCustomFormatDTO(404, "The Customer Data && the product data both not found",
						"customer and product not found");
			}
		}
		return response;
	}

	@Override
	public Customer CustomerFindById(long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found in database."));
	}

	@Override
	public Customer deleteCustomerById(long id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		if(ObjectUtils.isEmpty(customer))
		{
			throw new CustomerNotFoundException("The customer is not exist in database");
		}
		else {
			customerRepository.deleteById(id);
		}
		return customer;
	}


}
