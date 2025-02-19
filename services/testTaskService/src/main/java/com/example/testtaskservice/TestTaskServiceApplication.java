package com.example.testtaskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TestTaskServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TestTaskServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TestTaskServiceApplication.class, args);
        System.out.println("Go To http://localhost:8080/swagger-ui/");

     }

}
