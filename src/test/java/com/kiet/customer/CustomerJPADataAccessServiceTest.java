package com.kiet.customer;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);

    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void getAllCustomers() {
        // WHEN
        underTest.getAllCustomers();

        // THEN
        verify(customerRepository).findAll();
    }

    @Test
    void getById() {
        // GIVEN
        int id = 1;

        // WHEN
        underTest.getById(id);

        // THEN
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        // GIVEN
        Customer customer = new Customer(
                "john",
                "john@gmail.com", // for unique constraint
                20
        );

        // WHEN
        underTest.insertCustomer(customer);

        // THEN
        verify(customerRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        // GIVEN
        String email = "john@gmail.com";

        // WHEN
        underTest.existsPersonWithEmail(email);

        // THEN
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        // GIVEN
        int id = 1;

        // WHEN
        underTest.existsPersonWithId(id);

        // THEN
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void deleteCustomerById() {
        // GIVEN
        int id = 1;

        // WHEN
        underTest.deleteCustomerById(id);

        // THEN
        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        // GIVEN
        Customer customer = new Customer(
                1,
                "john",
                "john@gmail.com", // for unique constraint
                20
        );

        // WHEN
        underTest.updateCustomer(customer);

        // THEN
        verify(customerRepository).save(customer);
    }
}