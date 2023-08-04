package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping("/infor/{id}")
//    public String getAttachFacility(Model model, @PathVariable int id) {
//        InforPatientDTO inforPatientDTO = patientService.getInforById(id);
//        model.addAttribute("patientInforDTO", inforPatientDTO);
//        return "admin/account_information";
//    }
}
