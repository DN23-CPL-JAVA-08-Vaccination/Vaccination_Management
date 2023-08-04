package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.EmployeeCreateDTO;
import com.example.vaccination_management.validation.ChangePasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SecurityController {

    @Autowired
    private ChangePasswordValidator changePasswordValidator;

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
        modelAndView.addObject("accountDTO", new AccountDTO());
        return modelAndView;
    }

    @PostMapping("/change-password")
    public String save(@Validated @ModelAttribute AccountDTO accountDTO, BindingResult bindingResult, Model model, RedirectAttributes attributes) {

        changePasswordValidator.validate(accountDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", "Vui lòng nhập đúng các trường !");
            return "security/change_password";
        }

        try {
            // đổi mật khẩu

        } catch (Exception ex) {
            model.addAttribute("msg", "Mật khẩu không đúng !");
            return "security/change_password";
        }
        attributes.addFlashAttribute("msg", "Đổi mật khẩu thành công !");
        return "redirect:/infor-account";

    }

}
