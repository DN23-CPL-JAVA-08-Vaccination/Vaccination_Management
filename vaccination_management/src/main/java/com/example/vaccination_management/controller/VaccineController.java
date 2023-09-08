package com.example.vaccination_management.controller;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import com.example.vaccination_management.service.impl.VaccineService;
import com.example.vaccination_management.service.impl.VaccineTypeService;
import com.example.vaccination_management.utils.FormatPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/vaccine")
@Controller
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    @Autowired
    private VaccineTypeService vaccineTypeService;

    private String getPriceFormat;

    /**
     * LoanHTP
     * Displays a paginated list of vaccination histories for a specific patient.
     */
    @GetMapping("/list-vaccine")
    public String listVaccineAndSearch(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "4") int size,
                                       @RequestParam(value = "search", required = false) String searchQuery,
                                       Model model) {
        List<Vaccine> vaccines;
        long totalVaccines;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            vaccines = vaccineService.getVaccinesByPageAndSearch(page, size, searchQuery);
            totalVaccines = vaccineService.getTotalVaccinesBySearch(searchQuery);
        } else {
            vaccines = vaccineService.getVaccinesByPage(page, size);
            totalVaccines = vaccineService.getTotalVaccines();
        }

        List<VaccineType> vaccineTypes = vaccineTypeService.showVaccineType();
        model.addAttribute("vaccineTypes", vaccineTypes);

        int totalPages = (int) Math.ceil((double) totalVaccines / size);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("vaccines", vaccines);
        model.addAttribute("currentPage", page);

        return "/user/vaccine/vaccine-vaccinetype-list";
    }

    /**
     * LoanHTP
     * Displays details of a specific vaccination history.
     */
    @GetMapping("/list-vaccine/{vaccine_type_id}")
    public String getVaccineByVaccineTypeAndSearch(@PathVariable("vaccine_type_id") int vaccine_type_id,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "4") int size,
                                                   @RequestParam(value = "search", required = false) String searchQuery,
                                                   Model model) {
        VaccineType vaccineType = vaccineTypeService.findVaccineTypeById(vaccine_type_id);
        model.addAttribute("vaccine_type_id", vaccine_type_id);
//        model.addAttribute("vaccineTypeName", vaccineType.getName());

        List<Vaccine> vaccines;
        long totalVaccines;

        if (searchQuery != null ) {
            vaccines = vaccineService.getVaccinesByPageAndVaccineTypeAndSearch(page, size, vaccineType, searchQuery);
            totalVaccines = vaccineService.getTotalVaccinesByVaccineTypeAndSearch(vaccineType, searchQuery);
        } else {
            vaccines = vaccineService.getVaccinesByPageAndVaccineType(page, size, vaccineType);
            totalVaccines = vaccineService.getTotalVaccinesByVaccineType(vaccineType);
        }

        List<VaccineType> vaccineTypes = vaccineTypeService.showVaccineType();
        model.addAttribute("vaccineTypes", vaccineTypes);

        int totalPages = (int) Math.ceil((double) totalVaccines / size);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("vaccines", vaccines);
        model.addAttribute("currentPage", page);

        return "/user/vaccine/vaccine-vaccinetype-list";
    }

}
