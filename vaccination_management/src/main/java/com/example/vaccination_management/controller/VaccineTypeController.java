package com.example.vaccination_management.controller;

import com.example.vaccination_management.dto.IVaccineDTO;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.service.IVaccineService;
import com.example.vaccination_management.service.IVaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VaccineTypeController {
    @Autowired
    IVaccineTypeService iVaccineTypeService;
    @Autowired
    IVaccineService iVaccineService;
    /**
     * QuangVT
     * get information of vaccine type
     */
    @GetMapping("/doctor/vaccineType")
    public String schedule(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size,@RequestParam(defaultValue = "", required = false) String strSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VaccineType> vaccineTypePage = iVaccineTypeService.findAllVaccine('%' + strSearch + '%',pageable);
        model.addAttribute("vaccineTypeList", vaccineTypePage);
        return "doctors/vaccineType";
    }

    /**
     * QuangVT
     * get information of vaccine by type
     */
    @GetMapping("/doctor/vaccineByType")
    public String getByType(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size, @RequestParam Integer type) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IVaccineDTO>  vaccinePage = iVaccineService.getVaccineByType(pageable,type);
        model.addAttribute("vaccineList", vaccinePage);
        return "doctors/vaccine";
    }
}
