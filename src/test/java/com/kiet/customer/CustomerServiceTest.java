package com.kiet.customer;

import com.kiet.exception.DuplicateResourceException;
import com.kiet.exception.RequestValidationException;
import com.kiet.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;
    @Mock
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDAO);
    }


    @Test
    void getAllCustomers() {
        // WHEN
        underTest.getAllCustomers();

        // THEN
        verify(customerDAO).getAllCustomers();
    }

    @Test
    void getById() {
        // GIVEN
        int id  = 1;
        Customer customer = new Customer(
                1,"john", "john@gmail.com", 90
        );

        // mock the underlying DAO method
        when(customerDAO.getById(id)).thenReturn(Optional.of(customer));

        // WHEN
        var res = underTest.getById(id);

        // THEN
        assertThat(res).isEqualTo(customer);
    }

    @Test
    void willThrowWhengetByIdReturnsEmptyOptional() {
        // GIVEN
        int id  = 1;

        // mock the underlying DAO method
        when(customerDAO.getById(id)).thenReturn(Optional.empty());

        // THEN
        assertThatThrownBy(() -> underTest.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));
    }

    @Test
    void insertCustomer() {
        // GIVEN
        String email = "john@gmail.com";
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "john", email, 20
        );
        when(customerDAO.existsPersonWithEmail(email)).thenReturn(false);

        // WHEN
        underTest.insertCustomer(request);

        // THEN
        // Mockito ArgumentCaptor is used to capture arguments for mocked methods
        // usually used when we can’t access the argument outside of the method we’d like to test.
        // in this case insertCustomer makes a new Customer object before calling the DAO method

        // captures the customer value that was inserted into underTest.insertCustomer
        // to the customer class, even if the request is a CustomerRegistrationRequest type
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // value capturing happens here with argumentCaptor.capture()
        verify(customerDAO).insertCustomer(argumentCaptor.capture());
        Customer captured = argumentCaptor.getValue();

        // assert captured value is the same as request
        assertThat(captured.getId()).isNull();
        assertThat(captured.getName()).isEqualTo(request.name());
        assertThat(captured.getEmail()).isEqualTo(request.email());
        assertThat(captured.getAge()).isEqualTo(request.age());
    }

    @Test
    void WillThrowDuplicateEmailInsertCustomer() {
        // GIVEN
        String email = "john@gmail.com";
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "john", email, 20
        );
        when(customerDAO.existsPersonWithEmail(email)).thenReturn(true);

        // WHEN
        assertThatThrownBy(() -> underTest.insertCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        verify(customerDAO, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        // GIVEN
        Integer id = 1;
        when(customerDAO.existsPersonWithId(id)).thenReturn(true);

        // WHEN
        underTest.deleteCustomerById(id);

        // THEN
        verify(customerDAO).deleteCustomerById(id);
    }

    @Test
    void willThrowWhenDeleteCustomerByIdNotFound() {
        // GIVEN
        Integer id = 1;
        when(customerDAO.existsPersonWithId(id)).thenReturn(false);

        // THEN
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));

        verify(customerDAO, never()).deleteCustomerById(id);
    }

    @Test
    void updateCustomerByIdAllFields() {
        // GIVEN
        Integer id = 1;
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "james", "jimbo@gmail.com", 19
        );
        Customer customer = new Customer(
                id,"john", "john@gmail.com", 90
        );
        when(customerDAO.getById(id)).thenReturn(Optional.of(customer));
        when(customerDAO.existsPersonWithEmail(request.email())).thenReturn(false);

        // WHEN
        underTest.updateCustomerById(id, request);

        // THEN
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());

        Customer updateInput = customerArgumentCaptor.getValue();

        assertThat(updateInput.getId()).isEqualTo(id);
        assertThat(updateInput.getName()).isEqualTo(request.name());
        assertThat(updateInput.getEmail()).isEqualTo(request.email());
        assertThat(updateInput.getAge()).isEqualTo(request.age());
    }

    @Test
    void WillThrowWhenUpdateCustomerByIdNoChanges() {
        // GIVEN
        Integer id = 1;
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "john", "john@gmail.com", 90
        );
        Customer customer = new Customer(
                id,"john", "john@gmail.com", 90
        );
        when(customerDAO.getById(id)).thenReturn(Optional.of(customer));

        // WHEN
        assertThatThrownBy(() -> underTest.updateCustomerById(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No changes made");

        // THEN
        verify(customerDAO, never()).updateCustomer(any());
    }

    @Test
    void WillThrowWhenUpdateCustomerByIdEmailDuplicate() {
        // GIVEN
        Integer id = 1;
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "james", "jazz@gmail.com", 90
        );
        Customer customer = new Customer(
                id,"john", "john@gmail.com", 90
        );
        when(customerDAO.getById(id)).thenReturn(Optional.of(customer));
        when(customerDAO.existsPersonWithEmail(request.email())).thenReturn(true);

        // WHEN
        assertThatThrownBy(() -> underTest.updateCustomerById(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        // THEN
        verify(customerDAO, never()).updateCustomer(any());
    }
}