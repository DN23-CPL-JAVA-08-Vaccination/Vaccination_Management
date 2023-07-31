package com.example.vaccination_management.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView showMain() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView homeAdminPage() {
        ModelAndView modelAndView = new ModelAndView("admin/home_admin");
        return modelAndView;
    }

    @GetMapping("/patient")
    public ModelAndView homePatientPage() {
        ModelAndView modelAndView = new ModelAndView("home_patient");
        return modelAndView;
    }
}
