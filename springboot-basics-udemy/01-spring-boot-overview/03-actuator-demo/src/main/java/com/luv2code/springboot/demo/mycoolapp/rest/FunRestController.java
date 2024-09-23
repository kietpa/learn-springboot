package com.luv2code.springboot.demo.mycoolapp.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
    @Value("${coach.name}")
    private String coachName;

    @Value("${team.name}")
    private String teamName;

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

    @GetMapping("/teaminfo")
    public String getTeamInfo() {
        return String.format("Coach: %s, Team Name: %s",coachName,teamName);
    }
}
