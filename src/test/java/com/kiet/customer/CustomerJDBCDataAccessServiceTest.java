package com.kiet.customer;

import com.kiet.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        // fresh instance for test
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void getAllCustomers() {
        // GIVEN
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "-" + UUID.randomUUID(), // for unique constraint
                20
        );

        underTest.insertCustomer(customer);

        // WHEN
        List<Customer> actual = underTest.getAllCustomers();

        // THEN
        assertThat(actual).isNotEmpty();
    }

    @Test
    void getById() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email, // for unique constraint
                20
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        Optional<Customer> actual = underTest.getById(id);
        System.out.println(actual);

        // THEN
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyGetById() {
        // GIVEN
        int id = -1;

        // WHEN
        var actual = underTest.getById(id);

        // THEN
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email, // for unique constraint
                20
        );

        // WHEN
        underTest.insertCustomer(customer);

        Optional<Customer> actual = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();

        // THEN
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getName()).isEqualTo(customer.getName());
        });
    }

    @Test
    void existsPersonWithEmail() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email, // for unique constraint
                20
        );

        underTest.insertCustomer(customer);

        // WHEN
        boolean actual = underTest.existsPersonWithEmail(email);

        // THEN
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithId() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email, // for unique constraint
                20
        );

        underTest.insertCustomer(customer);
        Integer id = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        boolean actual = underTest.existsPersonWithId(id);

        // THEN
        assertThat(actual).isTrue();
    }

    @Test
    void deleteCustomerById() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email, // for unique constraint
                20
        );

        underTest.insertCustomer(customer);
        Integer id = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // WHEN
        underTest.deleteCustomerById(id);
        boolean actual = underTest.existsPersonWithId(id);

        // THEN
        assertThat(actual).isFalse();
    }

    @Test
    void updateCustomer() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email, // for unique constraint
                20
        );
        underTest.insertCustomer(customer);
        Integer id = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        Customer customer1 = new Customer(
                id,
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                22
        );
        underTest.updateCustomer(customer1);
        Optional<Customer> actual = underTest.getById(id);

        // THEN
        assertThat(actual).isPresent().hasValue(customer1);
    }
}