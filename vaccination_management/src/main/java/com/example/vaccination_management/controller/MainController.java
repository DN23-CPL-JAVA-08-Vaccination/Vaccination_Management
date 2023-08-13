package com.example.vaccination_management.controller;


import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.dto.InfoEmployeeAccountDTO;
import com.example.vaccination_management.dto.InforEmployeeDTO;
import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.security.AccountDetailService;
import com.example.vaccination_management.service.IEmployeeService;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import com.example.vaccination_management.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    AccountDetailService accountDetailService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    IPatientService patientService;

    @Autowired
    IVaccinationHistoryService iVaccinationHistoryService;
    @Autowired
    IVaccineService iVaccineService;
    private String getDefaultYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }


    @GetMapping("/")
    public String showMain() {
//        ModelAndView modelAndView = new ModelAndView("index");
//        return modelAndView;
        return "redirect:/vaccine/list-vaccine";
    }

    @GetMapping("/admin")
    public ModelAndView homeAdminPage() {
        ModelAndView modelAndView = new ModelAndView("admin/home_admin");
        return modelAndView;
    }

    /**
     * QuangVT
     * get information for dashboard
     */
    @GetMapping("/doctor")
    public String home(Model model, @RequestParam(defaultValue = "") String year) {
        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);

        IVaccinationHistoryDTO ivacci = iVaccinationHistoryService.countVaccination();
        model.addAttribute("countVacc", ivacci);
        long count = iVaccineService.count();
        if (year.isEmpty()) {
            year = getDefaultYear();
        }
        List<Integer> listChart = iVaccinationHistoryService.getDataForChart(year);

        model.addAttribute("chartList", listChart);
        model.addAttribute("countVaccine", count);

        return "doctors/homedoctor";
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

    /**
     * ThangLV
     * show information of employee, admin after login
     */
    @GetMapping("/infor-account/employee")
    public String showInforAccountEmployee(Model model) {
        String username = accountDetailService.getCurrentUserName();
        InfoEmployeeAccountDTO employeeDTO = employeeService.getInforByUsername(username);
        model.addAttribute("employeeDTO", employeeDTO);
        return "admin/employee_infor_account";
    }


    /**
     * ThangLV
     * show information of user(patient) after login
     */
    @GetMapping("/infor-account/patient")
    public String showInforAccountPatient(Model model) {
        String username = accountDetailService.getCurrentUserName();
        InforPatientDTO inforPatientDTO = patientService.getInforByUsername(username);
        model.addAttribute("patientDTO", inforPatientDTO);
        return "patient_infor_account";
    }

    /**
     * QuangVT
     * get information of datachart by year
     */
    @GetMapping("/chart-data")
    @ResponseBody
    public List<Integer> getChartData(@RequestParam(defaultValue = "") String year) {
        return iVaccinationHistoryService.getDataForChart(year);
    }
}
