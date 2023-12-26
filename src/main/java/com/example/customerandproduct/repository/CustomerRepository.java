package com.example.customerandproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customerandproduct.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
