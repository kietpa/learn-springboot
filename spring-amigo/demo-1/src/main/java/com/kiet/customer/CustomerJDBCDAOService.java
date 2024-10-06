package com.kiet.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDAOService implements CustomerDAO{

    private final JdbcTemplate jdbcTemplate; // jdbc's version of JPA's repository

    public CustomerJDBCDAOService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> getAllCustomers() {
        var sql = """
                    select id, name, email, age
                    from customer
                """;

        // map to object ofc
        // rs = result set object that maintains a cursor
        //      pointing to its current row of data
        RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
            return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age")
            );
        };
        List<Customer> res = jdbcTemplate.query(sql, customerRowMapper);

        return res;
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                    insert into customer(name, email, age)
                    values(?, ?, ?)
                """;

        // returns row updated count
        int res = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
                );

        System.out.println("How many rows updated" + res);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return false;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return false;
    }

    @Override
    public void deleteCustomerById(Integer id) {

    }

    @Override
    public void updateCustomer(Customer customer) {

    }
}
