package com.kiet.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component     // map to object ofc
public class CustomerRowMapper implements RowMapper<Customer> {

    // replaces lambda func before
    // rs = result set object that maintains a cursor
    //      pointing to its current row of data
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        );
    }
}
