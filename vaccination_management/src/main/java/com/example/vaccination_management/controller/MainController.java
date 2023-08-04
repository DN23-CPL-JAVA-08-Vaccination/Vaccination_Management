package com.example.vaccination_management.controller;


import com.example.vaccination_management.dto.InfoEmployeeAccountDTO;
import com.example.vaccination_management.dto.InforEmployeeDTO;
import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.security.AccountDetailService;
import com.example.vaccination_management.service.IEmployeeService;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

@Controller
public class MainController {

    @Autowired
    AccountDetailService accountDetailService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    IPatientService patientService;

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

    /**
     * ThangLV
     * get information of employee, user after login
     */
    @GetMapping("/infor-account")
    public String getAttachFacility() {
        Collection<GrantedAuthority> authorities = accountDetailService.getAuthorities();
        String role = "";
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }

        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_EMPLOYEE")) {

            return "redirect:/infor-account/employee";
        }
        if (role.equals("ROLE_USER")) {
            return "redirect:/infor-account/patient";
        }
        return "home_patient";
    }

    @GetMapping("/infor-account/employee")
    public String showInforAccountEmployee(Model model) {
        String username = accountDetailService.getCurrentUserName();
        InfoEmployeeAccountDTO employeeDTO = employeeService.getInforByUsername(username);
        model.addAttribute("employeeDTO", employeeDTO);
        return "admin/employee_infor_account";
    }

    @GetMapping("/infor-account/patient")
    public String showInforAccountPatient(Model model) {
        String username = accountDetailService.getCurrentUserName();
        InforPatientDTO inforPatientDTO = patientService.getInforByUsername(username);
        model.addAttribute("patientDTO", inforPatientDTO);
        return "patient_infor_account";
    }




}
