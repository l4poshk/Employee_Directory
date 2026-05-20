package com.example.employeedirectory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeDirectoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeDirectoryApplication.class, args);
    }
}
