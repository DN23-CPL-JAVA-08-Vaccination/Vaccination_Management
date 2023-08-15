package com.example.vaccination_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
public class SecurityRestController {

    @GetMapping("/customer/api")
    public String getAttachFacility(@RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String password) {

        if (username == "") {
            System.out.println("U rõng");

        }
        if (password == "") {
            System.out.println("P rõng");
        }
        return "Rỗng";
    }
}
