package com.luv2code.springboot.demo.mycoolapp.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
    // expose a "/" endpoint that returns "Hello World!"
    @GetMapping("/")
    public String sayHello() {
        return "Hello World!";
    }

    // expose new "workout" endpoint

    @GetMapping("/workout")
    public String getDailyWorkout() {
        return "run a hard 5k";
    }
}
