package com.example.customerandproduct.controller;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.customerandproduct.dto.CustomFormatDTO;
import com.example.customerandproduct.dto.CustomerDTO;
import com.example.customerandproduct.model.Customer;
import com.example.customerandproduct.service.CustomerService;

import java.nio.file.Path;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	private CustomFormatDTO customFormatDTO;
	
	private static final String UPLOAD_DIR = "C:\\Users\\vasudevpanchani\\Desktop\\Core_Java-main\\demo";
	
	@GetMapping("/displayCustomer")
	@ResponseBody
	public ResponseEntity<CustomFormatDTO> getAllDetails(){
		customFormatDTO = customerService.getAllDetails();
		return new ResponseEntity<CustomFormatDTO>(customFormatDTO, HttpStatusCode.valueOf(customFormatDTO.getStatus()));
	}
	
	@PostMapping("/addCustomer")
	@ResponseBody
	public ResponseEntity<CustomFormatDTO> addCustomer(@RequestBody Customer customer) {
		customFormatDTO = customerService.addCustomer(customer);
		return new ResponseEntity<CustomFormatDTO>(customFormatDTO, HttpStatusCode.valueOf(customFormatDTO.getStatus()));
	}
	
	@DeleteMapping("/deleteCustomer")
	@ResponseBody
	public Customer deleteCustomer(@RequestParam("customerId") long customerid){
		/*
		 * customerService.deleteCustomer(customerid); CustomFormatDTO
		 * customFormatDTO=new CustomFormatDTO();
		 * customFormatDTO.setMessage("Data deleted succesfully...");
		 * customFormatDTO.setData("ok"); customFormatDTO.setStatus(200); return new
		 * ResponseEntity<CustomFormatDTO>(customFormatDTO,HttpStatusCode.valueOf(200));
		 */
		return  customerService.deleteCustomerById(customerid);
	}
	
	@PutMapping("/updateCustomer")
	@ResponseBody
	public ResponseEntity<CustomFormatDTO> updateCustomer(@RequestBody Customer customer) {
		customFormatDTO = customerService.updateCustomer(customer);
		return new ResponseEntity<CustomFormatDTO>(customFormatDTO, HttpStatusCode.valueOf(customFormatDTO.getStatus()));
	}
	
	@PutMapping("/addDataById")
	@ResponseBody
	public ResponseEntity<CustomFormatDTO>  addDataById(@RequestParam("customerId") long customerid,@RequestParam("productId") long productid) {
		customFormatDTO = customerService.addDataById(customerid, productid);
		return new ResponseEntity<CustomFormatDTO>(customFormatDTO, HttpStatusCode.valueOf(customFormatDTO.getStatus()));
	}
	
	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
		try {
			File directory=new File(UPLOAD_DIR);
			
			if (!directory.exists()) {	
				directory.mkdirs();
			}
			
			String fileName = file.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
			
			Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/customer/download/")
					.path(fileName).toUriString();

			return ResponseEntity.ok("File uploaded successfully. Download URL: " + fileDownloadUri);
		}catch (IOException ex) {
			return ResponseEntity.status(500).body("Could not upload the file: " + ex.getMessage());
		}
	}
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Object> downloadFile(@PathVariable("fileName") String fileName) {
		try {

			Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
			File file = filePath.toFile();

			if (file.exists()) {
				org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
				headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDispositionFormData("attachment", fileName);

				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.body(new FileSystemResource(file));
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception ex) {
			return ResponseEntity.status(500).body("Failed to download the file: " + ex.getMessage());
		}
	}
	
	@GetMapping("/getCustomerById/{id}")
	@ResponseBody
	public CustomFormatDTO getCustomerFindById(@PathVariable("id") long id) {
		Customer customer=customerService.CustomerFindById(id);
		return new CustomFormatDTO(200,"ok",customer);
	}
	
}
