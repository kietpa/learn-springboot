package com.kiet.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // GIVEN
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("john");
        when(resultSet.getString("email")).thenReturn("john@gmail.com");
        when(resultSet.getInt("age")).thenReturn(19);

        // WHEN
        Customer actual = customerRowMapper.mapRow(resultSet, 1);

        // THEN
        Customer expected = new Customer(
                1, "john", "john@gmail.com", 19
        );

        assertThat(actual).isEqualTo(expected);
    }
}