package com.example.vaccination_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class VaccinationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccinationManagementApplication.class, args);
    }

}
