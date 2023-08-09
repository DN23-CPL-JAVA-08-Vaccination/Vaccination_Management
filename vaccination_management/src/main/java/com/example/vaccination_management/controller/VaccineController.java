package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IVaccineDTO;
import com.example.vaccination_management.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class VaccineController {
    @Autowired
    IVaccineService iVaccineService;
    /**
     * QuangVT
     * get information of vaccine
     */
    @GetMapping("/doctor/vaccine")
    public String getAll(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size , @RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IVaccineDTO>  vaccinePage = iVaccineService.searchVaccine(pageable,'%' +strSearch + '%');
        model.addAttribute("vaccineList", vaccinePage);
        return "doctors/vaccine";
    }


}
