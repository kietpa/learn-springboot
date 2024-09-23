package com.luv2code.springcodedemo.config;

import com.luv2code.springcodedemo.common.Coach;
import com.luv2code.springcodedemo.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {

    @Bean("aquatic")
    public Coach swimCoach() {
        return new SwimCoach();
    }
}
