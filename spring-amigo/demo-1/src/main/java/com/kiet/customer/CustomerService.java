package com.kiet.customer;

import java.util.List;
import java.util.Optional;
@
public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getById(Integer id) {
        return customerDAO.getById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "customer with id [%s] not found".formatted(id)
                        ));

    }
}
