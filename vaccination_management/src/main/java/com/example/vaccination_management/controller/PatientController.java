package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.PatientDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IPatientService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    IAccountService iAccount;

    @Autowired
    IPatientService iPatient;

    @GetMapping("/requires_account")
    public String viewFormRequires(ModelMap model, HttpServletRequest request){
        String message=request.getParameter("message");
        model.addAttribute("message",message);
        model.addAttribute("patient", new PatientDTO());
        return "Patient/requires_account";
    }

    @PostMapping("/insert_patient")
    public String insertPatient(@ModelAttribute("patientDTO") PatientDTO patientDTO,
                                RedirectAttributes redirectAttributes){
        patientDTO.setEnableFlag(false);
        patientDTO.setAccount(iAccount.findLatestAccountId());
      iPatient.insertPatient(patientDTO.getName(),patientDTO.getGender(),patientDTO.getPhone(),patientDTO.getAddress(),patientDTO.getBirthday(),patientDTO.getHealthInsurance(),patientDTO.getGuardianName(),
              patientDTO.getGuardianPhone(),patientDTO.getEnableFlag(),patientDTO.getAccount());
        redirectAttributes.addAttribute("message", "Request sent successfully!!!");
      return "redirect:/patient/requires_account";
    }


    @GetMapping("/view_patient")
    public String view(Model model){

        return "Admin/Patient";
    }

}
