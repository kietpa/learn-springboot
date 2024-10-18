package com.kiet.journey;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.kiet.customer.Customer;
import com.kiet.customer.CustomerRegistrationRequest;
import com.kiet.customer.CustomerUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// file named with IT suffix to exclude test from maven surefire
// in the test we don't invoke controller methods but http requests
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIT {

    @Autowired // for http requests
    private WebTestClient webTestClient;

    private final Faker faker = new Faker();
    private static final Random RANDOM = new Random();
    private static final String URI = "/api/v1/customers";

    @Test
    void canRegisterCustomer() {
        // create registration request
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.firstName() + "-" + UUID.randomUUID() + "@gmail.com";
        int age = RANDOM.nextInt(1,50);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name, email, age
        );

        // add customer
        webTestClient.post()
                .uri(URI) // no host needed
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // make sure customer is present
        // get all customer
        List<Customer> allCustomers = webTestClient.get()
                .uri(URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        Customer expected = new Customer(
                name, email, age
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expected);

        // get customer by id
        var id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        expected.setId(id);

        webTestClient.get()
                .uri(URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {})
                .isEqualTo(expected);
    }

    @Test
    void canDeleteCustomer() {
        // create registration request
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.firstName() + "-" + UUID.randomUUID() + "@gmail.com";
        int age = RANDOM.nextInt(1,50);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name, email, age
        );

        // add customer
        webTestClient.post()
                .uri(URI) // no host needed
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customer
        List<Customer> allCustomers = webTestClient.get()
                .uri(URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        // get customer by id
        var id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        webTestClient.delete()
                .uri(URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri(URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
        // create registration request
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.firstName() + "-" + UUID.randomUUID() + "@gmail.com";
        int age = RANDOM.nextInt(1,50);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name, email, age
        );

        // add customer
        webTestClient.post()
                .uri(URI) // no host needed
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customer for id
        List<Customer> allCustomers = webTestClient.get()
                .uri(URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        // get id
        var id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // update
        String name2 = fakerName.fullName();
        String email2 = fakerName.firstName() + "-" + UUID.randomUUID() + "@gmail.com";
        int age2 = RANDOM.nextInt(1,50);
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                name2, email2, age2
        );

        Customer expected = new Customer(
                id, name2, email2, age2
        );

        webTestClient.put()
                .uri(URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get by id
//        webTestClient.get()
//                .uri(URI + "/{id}", id)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(new ParameterizedTypeReference<Customer>() {})
//                .isEqualTo(expected);

        Customer updatedCustomer = webTestClient.get()
                .uri(URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        assertThat(updatedCustomer).isEqualTo(expected);
    }
}
