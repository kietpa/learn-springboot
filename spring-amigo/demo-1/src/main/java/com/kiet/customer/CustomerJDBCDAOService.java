package com.kiet.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDAOService implements CustomerDAO{

    private final JdbcTemplate jdbcTemplate; // jdbc's version of JPA's repository
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDAOService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> getAllCustomers() {
        var sql = """
                    select id, name, email, age
                    from customer
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        var sql = """
                    select id, name, email, age
                    from customer
                    where id = ?
                """;

        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
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
        var sql = """
                    SELECT COUNT(*) from customer
                    where email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        return count > 0 && count != null;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        var sql = """
                    select count(*)
                    from customer
                    where id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count > 0 && count != null;
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                    delete from customer
                    where id = ?
                """;

        int res = jdbcTemplate.update(sql, id);
        System.out.println("deleted " + res + " rows");
    }

    @Override
    public void updateCustomer(Customer update) {
        if (update.getName() != null) {
            var sql = "UPDATE customer set name = ? where id = ?";
            int res = jdbcTemplate.update(sql, update.getName(), update.getId());
            System.out.println("updated name");
        }

        if (update.getEmail() != null) {
            var sql = "UPDATE customer set email = ? where id = ?";
            int res = jdbcTemplate.update(sql, update.getEmail(), update.getId());
            System.out.println("updated email");
        }

        if (update.getAge() != null) {
            var sql = "UPDATE customer set age = ? where id = ?";
            int res = jdbcTemplate.update(sql, update.getAge(), update.getId());
            System.out.println("updated age");
        }
    }
}
