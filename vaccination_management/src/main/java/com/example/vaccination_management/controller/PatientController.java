package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IPatientDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.service.IPatientService;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PatientController {
    @Autowired
    IPatientService iPatientService;
    @Autowired
    IVaccinationHistoryService iVaccinationHistoryService;
    @GetMapping("/doctor/patient")
    public String getAllPatient(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size , @RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IPatientDTO> patientPage = iPatientService.getPatients(pageable,'%' +strSearch + '%');
        model.addAttribute("patientList", patientPage);
        return "doctors/patient";
    }

    @GetMapping("/doctor/patient/view")
    public String detailPatient(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "", required = false) String strSearch,
                                @RequestParam Integer id) {

        Patient patient = iPatientService.getPatientById(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastTime").descending());
        Page<IVaccinationHistoryDTO>  list = iVaccinationHistoryService.getVaccinationByPatient(id,pageable);
        model.addAttribute("patientID",id);
        model.addAttribute("patient",patient);
        model.addAttribute("vaccinationList", list);
        return "doctors/detailPatient";
    }

}
