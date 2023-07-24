package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.EmployeeListDTO;
import com.example.vaccination_management.dto.PatientInforDTO;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/patient")
@Controller
public class PatientController {

    @Autowired
    IPatientService patientService;

    @GetMapping("/infor/{id}")
    public String getAttachFacility(Model model, @PathVariable int id) {
        PatientInforDTO patientInforDTO = patientService.getInforById(id);
        model.addAttribute("patientInforDTO", patientInforDTO);
        return "admin/account-information";
    }
}
