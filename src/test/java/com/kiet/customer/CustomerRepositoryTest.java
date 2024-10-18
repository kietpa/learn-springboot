package com.kiet.customer;

import com.kiet.AbstractTestcontainers;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // loads beans needed for JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // disable embedded DB
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired // auto init repo with testcontainers when class extends AbstractTestcontainers
    private CustomerRepository underTest;

    @BeforeEach
    void setUp() {

        // underTest.deleteAll();

    }

    @Test
    void existsCustomerByEmail() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email, // for unique constraint
                20
        );

        underTest.save(customer);

        // WHEN
        var res = underTest.existsCustomerByEmail(email);

        // THEN
        assertThat(res).isTrue();
    }

    @Test
    void existsCustomerById() {
        // GIVEN
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email, // for unique constraint
                20
        );

        underTest.save(customer);
        Integer id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        var res = underTest.existsCustomerById(id);

        // THEN
        assertThat(res).isTrue();
    }
}