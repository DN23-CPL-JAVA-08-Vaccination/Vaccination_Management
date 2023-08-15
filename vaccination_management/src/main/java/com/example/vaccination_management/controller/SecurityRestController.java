package com.example.vaccination_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/customer/api")
public class SecurityRestController {

    @GetMapping("/detail/{id}")
    public String getAttachFacility(@RequestParam String username, @RequestParam(defaultValue = "") String password) {

        if (username == "") {
            return "";
        }
        return "";
    }
}
