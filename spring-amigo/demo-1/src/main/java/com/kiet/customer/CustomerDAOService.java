package com.kiet.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOService implements CustomerDAO{

    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(1,"Alex", "alex@gmail.com", 22);
        customers.add(alex);
        Customer jim = new Customer(2,"Jim", "jim@gmail.com", 23);
        customers.add(jim);
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
}
