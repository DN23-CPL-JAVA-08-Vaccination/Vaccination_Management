package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.security.AccountDetailService;
import com.example.vaccination_management.service.IAccountRoleService;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.validation.ChangePasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        modelAndView.addObject("accountDTO", new AccountDTO());
        return modelAndView;
    }

    /**
     * ThangLV
     * change password
     */
    @PostMapping("/change-password")
    public String save(@Validated @ModelAttribute AccountDTO accountDTO, BindingResult bindingResult, Model model, RedirectAttributes attributes) {

        changePasswordValidator.validate(accountDTO, bindingResult);
        String username = accountDetailService.getCurrentUserName();
        accountDTO.setUsername(username);
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", "Vui lòng nhập đúng các trường !");
            return "security/change_password";
        }
        boolean checkPassword = accountRoleService.checkPassword(accountDTO.getCurrentPassword(), accountDTO.getUsername());

        if (checkPassword) {
            accountService.changePasswordLogin(passwordEncoder.encode(accountDTO.getCurrentPassword()), accountDTO.getUsername());
            attributes.addFlashAttribute("msg", "Đổi mật khẩu thành công !");
            return "redirect:/infor-account";
        }
        model.addAttribute("msg", "Mật khẩu không đúng !");
        return "security/change_password";

    }

}
