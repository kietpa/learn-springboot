package com.kiet.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
        ) {
}
