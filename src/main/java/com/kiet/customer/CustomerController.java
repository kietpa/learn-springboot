package com.kiet.customer;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController  {
    private final CustomerService customerService;
    private final Validator validator;

    public CustomerController(CustomerService customerService, Validator validator) {
        this.customerService = customerService;
        this.validator = validator;
    }

    // @RequestMapping(path = "api/v1/customer", method = RequestMethod.GET)
    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public Customer getCustomerById(@Valid @Positive @PathVariable("customerId") Integer customerId) {
        return customerService.getById(customerId);
    }

    @PostMapping
    public void addCustomer(@Valid @RequestBody CustomerRegistrationRequest request) {
//        Set<ConstraintViolation<CustomerRegistrationRequest>> validate = validator.validate(request);
//        validate.forEach(error -> System.out.println(error.getMessage()));
//        if (!validate.isEmpty()) {
//            throw new ConstraintViolationException(validate);
//        }

        customerService.insertCustomer(request);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@Valid @Positive @PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(
            @Valid @Positive @PathVariable("customerId") Integer customerId,
            @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomerById(customerId, customerUpdateRequest);
    }

}
