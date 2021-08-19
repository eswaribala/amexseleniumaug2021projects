package com.amex.customerapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amex.customerapi.models.Customer;
import com.amex.customerapi.services.CustomerService;

@RestController
public class CustomerController {
    @Autowired
	private CustomerService customerService;
    
    //post
    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
    	
    	Customer customerObj=this.customerService.addCustomer(customer);
    	if(customerObj!=null) {
    		return ResponseEntity.status(HttpStatus.OK).body(customerObj);
    	}
    	else
    	{
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Not Added");
    	}
    }
    
    @GetMapping("/customers")
    public List<Customer> getCustomers(){
    	return this.customerService.getAllCustomers();
    }
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customerId") long customerId) {
    	Customer customerObj=this.customerService.getCustomerById(customerId);
    	if(customerObj!=null) {
    		return ResponseEntity.status(HttpStatus.OK).body(customerObj);
    	}
    	else
    	{
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Not Added");
    	}
    	
    }
    
    
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") long customerId) {
    	boolean status=this.customerService.deleteCustomerById(customerId);
    	if(status) {
    		return ResponseEntity.status(HttpStatus.OK).body("Customer Deleted for the Id"+customerId);
    	}
    	else
    	{
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Not Deleted");
    	}
    	
    }
    
}
