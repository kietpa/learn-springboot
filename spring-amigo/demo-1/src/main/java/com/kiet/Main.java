package com.kiet;

import com.github.javafaker.Faker;
import com.kiet.customer.Customer;
import com.kiet.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Faker faker = new Faker();
            Random rand = new Random();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();

            Customer customer1 = new Customer(firstName + " " + lastName, firstName + "." + lastName + "@gmail.com", rand.nextInt(18,50));

            customerRepository.save(customer1);
        };
    }
}
