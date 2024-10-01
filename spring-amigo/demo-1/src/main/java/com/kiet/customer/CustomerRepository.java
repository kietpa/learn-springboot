package com.kiet.customer;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> { // customer = type being used, Integer = primary key data type


    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Integer id);
}
