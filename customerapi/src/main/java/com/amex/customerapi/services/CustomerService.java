package com.amex.customerapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amex.customerapi.models.Customer;
import com.amex.customerapi.repositories.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	//insert or post
	public Customer addCustomer(Customer customer) {
		return this.customerRepository.save(customer);
	}

	//getall
	public List<Customer> getAllCustomers(){
		return this.customerRepository.findAll();
	}
	
	//get
	
	public Customer getCustomerById(long customerId) {
		return this.customerRepository.findById(customerId).orElse(null);
	}
	
	//delete
	public boolean deleteCustomerById(long customerId) {
		boolean status=false;
		this.customerRepository.deleteById(customerId);
		if(getCustomerById(customerId) == null)
			status=true;
		return status;
	}
}
