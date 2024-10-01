package com.kiet.customer;

import com.kiet.exception.DuplicateResourceException;
import com.kiet.exception.RequestValidationException;
import com.kiet.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getById(Integer id) {
        return customerDAO.getById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "customer with id [%s] not found".formatted(id)
                        ));

    }

    public void insertCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        // check if email exists
        String email = customerRegistrationRequest.email();
        if (customerDAO.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken");
        }

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDAO.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id) {
        if (!customerDAO.existsPersonWithId(id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(id)
            );
        }

        customerDAO.deleteCustomerById(id);
    }

    public void updateCustomerById(Integer id, CustomerUpdateRequest customerUpdateRequest) {
        // get customer info
        Customer customer = getById(id);

        boolean changes = false;

        // update
        if (!customerUpdateRequest.name().isEmpty() && !customerUpdateRequest.name().equals(customer.getName())) {
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }
        if (!customerUpdateRequest.email().isEmpty() && !customerUpdateRequest.email().equals(customer.getEmail())) {
            if (customerDAO.existsPersonWithEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }

            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }
        if (customerUpdateRequest.age() != null  && !customerUpdateRequest.age().equals(customer.getAge())) {
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No changes made");
        }

        customerDAO.updateCustomer(customer);
    }
}
