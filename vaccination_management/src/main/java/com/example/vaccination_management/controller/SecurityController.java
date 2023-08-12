package com.example.vaccination_management.controller;


import com.example.vaccination_management.dto.ChangeAccountDTO;
import com.example.vaccination_management.dto.LoginDTO;

import com.example.vaccination_management.security.AccountDetailService;
import com.example.vaccination_management.service.IAccountRoleService;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.validation.ChangePasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class SecurityController {

    @Autowired
    private ChangePasswordValidator changePasswordValidator;

    @Autowired
    IAccountService accountService;

    @Autowired
    IAccountRoleService accountRoleService;

    @Autowired
    AccountDetailService accountDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ModelAndView showFormLogin() {
        ModelAndView modelAndView = new ModelAndView("security/login");
        return modelAndView;
    }

    @GetMapping("/403")
    public ModelAndView authorizaFalse() {
        ModelAndView modelAndView = new ModelAndView("error");
        return modelAndView;
    }

    /**
     * ThangLV
     * show form change password
     */
    @GetMapping("/change-password")
    public ModelAndView showFormChangePassword() {
        ModelAndView modelAndView = new ModelAndView("security/change_password");
        modelAndView.addObject("changeAccountDTO", new ChangeAccountDTO());
        return modelAndView;
    }

    /**
     * ThangLV
     * change password
     */
    @PostMapping("/change-password")
    public String save(@Validated @ModelAttribute ChangeAccountDTO changeAccountDTO, BindingResult bindingResult, Model model, RedirectAttributes attributes) {

        changePasswordValidator.validate(changeAccountDTO, bindingResult);
        String username = accountDetailService.getCurrentUserName();
        changeAccountDTO.setUsername(username);
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", "Vui lòng nhập đúng các trường !");
            return "security/change_password";
        }
        boolean checkPassword = accountRoleService.checkPassword(changeAccountDTO.getCurrentPassword(), changeAccountDTO.getUsername());

        if (checkPassword) {
            accountService.changePasswordLogin(passwordEncoder.encode(changeAccountDTO.getNewPassword()), changeAccountDTO.getUsername());
            attributes.addFlashAttribute("msg", "Đổi mật khẩu thành công !");

            Collection<GrantedAuthority> authorities = accountDetailService.getAuthorities();
            String role = "";
            for (GrantedAuthority authority : authorities) {
                role = authority.getAuthority();
            }

            if (role.equals("ROLE_ADMIN")) {

                return "redirect:/admin";
            }
            if (role.equals("ROLE_EMPLOYEE")) {

                return "redirect:/doctor";
            }
            if (role.equals("ROLE_USER")) {
                return "redirect:/";
            }
            return "home_patient";
        }
        model.addAttribute("msg", "Mật khẩu không đúng !");
        return "security/change_password";

    }


    @RequestMapping("/login/error")
    public String loginError(Model model, @Valid LoginDTO loginDTO, BindingResult result) {
        System.out.println("error");
        System.out.println(loginDTO.getUsername());
        model.addAttribute("msg", "Đằng nhập thất bại");
        return "security/login";
    }
}
