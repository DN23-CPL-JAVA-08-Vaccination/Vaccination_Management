package com.example.vaccination_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {


    @GetMapping("/login")
    public ModelAndView showFormLogin(){
        ModelAndView modelAndView =  new ModelAndView("security/login");
        return modelAndView;
    }

    @GetMapping("/403")
    public ModelAndView authorizaFalse(){
        ModelAndView modelAndView =  new ModelAndView("error");
        return modelAndView;
    }

    @GetMapping("/change-password")
    public ModelAndView showFormChangePassword(){
        ModelAndView modelAndView =  new ModelAndView("security/change_password");
        return modelAndView;
    }

}
