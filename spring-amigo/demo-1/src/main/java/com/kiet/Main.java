package com.kiet;

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

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Customer alex = new Customer("Alex", "alex@gmail.com", 22);

            Customer jim = new Customer("Jim", "jim@gmail.com", 23);

            List<Customer> customers = List.of(alex, jim);

            customerRepository.saveAll(customers);
        };
    }
}
