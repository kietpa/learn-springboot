package com.kiet.customer;

import com.kiet.validation.Foo;
import jakarta.validation.constraints.*;

public record CustomerRegistrationRequest(
        @NotBlank(message = "please provide name")
        @Size(min = 4, message = "minimum name length is 4")
        String name,
        @Email(message = "please provide valid email")
        String email,
        @Min(value = 16, message = "Age is below the minimum")
        @Max(value = 100, message = "Please provide a valid age")
        Integer age
        ) {
}
