package com.microservice.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitConfigurationController {
    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitConfig limitConfig(){
        return new LimitConfig(configuration.minimum,configuration.maximum);
    }
}
