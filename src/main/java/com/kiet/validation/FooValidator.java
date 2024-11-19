package com.kiet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FooValidator implements
        ConstraintValidator<Foo, String> { // foo = annotation name, string = data type
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.equals("Foo");
    }
}
