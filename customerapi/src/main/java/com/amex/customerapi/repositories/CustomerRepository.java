package com.amex.customerapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amex.customerapi.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
